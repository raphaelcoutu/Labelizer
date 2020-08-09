import { Component, OnInit, Input } from '@angular/core';
import { LabelService } from '../_services/label.service';
import { Label } from '../_models/label';
import { Dataset } from '../_models/dataset';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-label-list',
  templateUrl: './label-list.component.html',
  styleUrls: ['./label-list.component.scss']
})
export class LabelListComponent implements OnInit {
  @Input() dataset: Dataset;

  labels: Label[];
  selectedLabel = new Label;
  formVisible = false;

  constructor(
    private labelService: LabelService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.loadLabels();
  }

  loadLabels() {
    this.labelService.getAllForDataset(this.activatedRoute.snapshot.params.id).subscribe((labels: Label[]) => this.labels = labels);
  }

  addLabel() {
    this.selectedLabel = new Label;
    this.formVisible = true;
  }

  editLabel(label: Label, event: MouseEvent) {
    event.preventDefault();
    this.selectedLabel = {...label};
    this.formVisible = true;
  }

  saveLabel(event) {
    event.preventDefault();

    if(this.selectedLabel.id) {
      this.labelService.put(this.selectedLabel.id, this.selectedLabel).subscribe(
        success => {
          this.formVisible = false;
          this.loadLabels()
        }
      )
    } else {
      let dataset = new Dataset;
      dataset.id = 1;
      this.selectedLabel.dataset = dataset;

      this.labelService.post(this.selectedLabel).subscribe(
        success => {
          this.formVisible = false;
          this.loadLabels()
        }
      )
    }
  }

  onFormHidden() {
    this.formVisible = false;
  }

}
