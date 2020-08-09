import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { DxSelectBoxComponent } from 'devextreme-angular/ui/select-box';
import { LabelBox } from '../_models/label-box';
import { Label } from '../_models/label';
import { Point } from '../_models/point';
import { LabelService } from '../_services/label.service';
import { KEY_CODE } from './key-code';
import { PhotoService } from '../_services/photo.service';
import { Photo } from '../_models/photo';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { concatMap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-labelizer',
  templateUrl: './labelizer.component.html',
  styleUrls: ['./labelizer.component.scss']
})
export class LabelizerComponent implements OnInit {

  @ViewChild('canvas', {static: true}) canvas: ElementRef<HTMLCanvasElement>;
  @ViewChild('labelSelectBox', {static: false}) labelSelectBox: DxSelectBoxComponent;
  photo: Photo;
  nextPhoto: Photo;
  backendUrl = environment.backendUrl;

  ctx: CanvasRenderingContext2D;
  labels: Label[];
  startPt: Point;
  endPt: Point;
  currentPt: Point;

  image: HTMLImageElement;
  position: string;
  scale = 75;
  mode = 'DRAW'


  selectedBox: LabelBox;
  selectedLabel: Label;
  keyEventDisabled = false;

  @HostListener('window:keyup', ['$event'])
  keyEvent(event: KeyboardEvent) {
    // Si le focus est ailleurs, on disable les keyEvents
    if(this.keyEventDisabled) return;

    if (event.key === KEY_CODE.DEL) {
      if (!this.selectedBox) return;
      this.deleteBox(this.selectedBox);
      this.selectedBox = null;
    } else if (event.key === KEY_CODE.D) {
      this.mode = 'DRAW'
    } else if (event.key === KEY_CODE.S) {
      this.mode = 'SELECT'
    } else if (event.key === KEY_CODE.A) {
      this.labelSelectBox.instance.focus();
    }
  }

  constructor(
    private photoService: PhotoService,
    private labelService: LabelService,
    private activatedRoute: ActivatedRoute,
    private router: Router) {}

  ngOnInit() {
    this.ctx = this.canvas.nativeElement.getContext('2d');
    this.initialize();
  }

  initialize() {
    this.photoService.get(this.activatedRoute.snapshot.params.id).pipe(
      concatMap((photo: Photo) => {
        this.photo = photo;
        this.image = new Image();
        this.image.src = this.backendUrl + '/files/' + this.photo.filename + '.' + this.photo.extension;
        this.image.onload = () => {
          this.canvas.nativeElement.width = this.image.width * this.scale / 100;
          this.canvas.nativeElement.height = this.image.height * this.scale / 100;
          this.ctx.scale(this.scale / 100, this.scale / 100);
          this.redrawCanvas();
        }
        return of(photo);
      }),
      concatMap((photo: Photo) => {
        this.labelService.getAllForDataset(photo.dataset.id).subscribe((labels: Label[]) => {
          this.labels = labels;
          this.selectedLabel = this.labels[0];
        })
        return this.photoService.getNextUnverifiedExcept(photo.dataset.id, photo.id)
      })
    ).subscribe(nextPhoto => this.nextPhoto = nextPhoto)
  }

  getPosition(event: MouseEvent) {
    let rect = this.canvas.nativeElement.getBoundingClientRect();
    let x = Math.round((event.clientX - rect.left) * 100 / this.scale);
    let y = Math.round((event.clientY - rect.top) * 100 / this.scale);
    return new Point(x, y)
  }

  onMouseDown(event: MouseEvent) {
    if (this.mode == 'DRAW') {
      if(!this.startPt) {
        this.startPt = this.getPosition(event)
      } else {
        //Situation où la souris aurait été out of bounds et qu'on reclic
        this.onMouseUp(event);
      }
    } else if (this.mode == 'SELECT') {
      for(let box of this.photo.labelBoxes) {
        if (this.isPointInsideBox(this.currentPt, box)) {
          this.selectedBox = box;
          this.redrawCanvas();
          return;
        }
        this.selectedBox = null;
      }
    }
  }

  onMouseUp(event: MouseEvent) {
    if (this.mode == 'DRAW') {
      this.endPt = this.getPosition(event)
      this.drawBox();
    }
  }

  onMouseMove(event: MouseEvent) {
    let mousePos = this.getPosition(event);
    this.position = mousePos.x + 'x' + mousePos.y;
    this.currentPt = this.getPosition(event)

    if (this.mode == 'DRAW') {
      if (!this.startPt) return
      this.drawTempBox()
    } else if(this.mode == 'SELECT') {
      //
    }
  }

  save() {
    this.photoService.put(this.photo.id, this.photo).subscribe(
      sucess => {},
      error => alert('Error!')
    )
  }

  saveAndVerify() {
    this.photo.verified = true;
    this.photoService.put(this.photo.id, this.photo).subscribe(
      sucess => {
        if (this.nextPhoto) {
          this.router.navigate(['/labelizer', this.nextPhoto.id]).then(() => {
            this.initialize();
          })
        } else {
          this.router.navigate(['/datasets', this.photo.dataset.id])
        }
      },
      error => alert('Error!')
    )
  }

  drawTempBox() {
    let x = Math.min(this.startPt.x, this.currentPt.x)
    let y = Math.min(this.startPt.y, this.currentPt.y)
    let width = Math.abs(this.startPt.x - this.currentPt.x)
    let height = Math.abs(this.startPt.y - this.currentPt.y)

    this.clearCanvas();
    this.redrawCanvas();
    this.ctx.strokeRect(x, y, width, height)
  }

  isPointInsideCanvas(pt: Point) {
    return pt.x > 0 && pt.y > 0 && pt.x < this.canvas.nativeElement.width && pt.y < this.canvas.nativeElement.height;
  }

  isPointInsideBox(pt: Point, box: any) {
    return pt.x > box.x && pt.y > box.y && pt.x < box.x + box.width && pt.y < box.y + box.height;
  }

  drawBox(){
    let x = Math.min(this.startPt.x, this.endPt.x)
    let y = Math.min(this.startPt.y, this.endPt.y)
    let width = Math.abs(this.startPt.x - this.endPt.x)
    let height = Math.abs(this.startPt.y - this.endPt.y)
    if(width < 10 || height < 10) {
      this.startPt = null;
      this.currentPt = null;
      return;
    }

    this.ctx.strokeRect(x, y, width, height)

    this.ctx.font = '16pt Arial'
    this.ctx.fillText(`${this.selectedLabel.name}`, x, y+16);
    this.photo.labelBoxes.unshift(new LabelBox(x, y, width, height, this.selectedLabel));
    this.startPt = null;
    this.endPt = null;
    this.currentPt = null;
  }

  clearCanvas() {
    this.ctx.clearRect(0, 0, this.canvas.nativeElement.width, this.canvas.nativeElement.height)
  }

  redrawCanvas() {
    this.ctx.drawImage(this.image, 0, 0)

    for(let box of this.photo.labelBoxes) {
      if (this.selectedBox != null && this.selectedBox === box) {
        this.ctx.strokeStyle = 'lightgreen'
        this.ctx.fillStyle = 'green'
      } else {
        this.ctx.strokeStyle = 'red'
        this.ctx.fillStyle = 'red'
      }
      this.ctx.font = '16pt Arial'
      this.ctx.fillText(`${box.label.name}`, box.x, box.y+16);
      this.ctx.strokeRect(box.x, box.y, box.width, box.height)
    }

    // Set default style
    this.ctx.strokeStyle = 'red'
    this.ctx.fillStyle = 'red'
  }

  clearBoxes() {
    this.photo.labelBoxes = [];
    this.selectedBox = null;
    this.redrawCanvas();
  }

  deleteBox(box: LabelBox) {
    let index = this.photo.labelBoxes.indexOf(box);
    this.photo.labelBoxes.splice(index, 1);
    this.redrawCanvas();
  }

  onScaleChange(event) {
    // On efface le canvas avant de resize
    this.clearCanvas();

    // On resize proportionnellement au zoom
    this.canvas.nativeElement.width = this.image.width * event.value/100;
    this.canvas.nativeElement.height = this.image.height * event.value/100;
    this.ctx.scale(event.value/100, event.value/100);

    // On redessine les éléments
    this.redrawCanvas();
    this.scale = event.value;
  }

  onLabelSelectBoxFocusIn(event) {
    this.keyEventDisabled = true;
    event.element.querySelector("input.dx-texteditor-input").select();
  }

  delay(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms))
  }

}
