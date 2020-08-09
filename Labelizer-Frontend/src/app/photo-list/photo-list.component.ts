import { Component, OnInit, Input } from '@angular/core';
import { Dataset } from '../_models/dataset';
import { Photo } from '../_models/photo';
import { PhotoService } from '../_services/photo.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-photo-list',
  templateUrl: './photo-list.component.html',
  styleUrls: ['./photo-list.component.scss']
})
export class PhotoListComponent implements OnInit {

  @Input() dataset: Dataset;

  photos: Photo[];
  backendUrl = environment.backendUrl;

  constructor(private photoService: PhotoService) { }

  ngOnInit() {
    this.photoService.getAllForDataset(1).subscribe((photos: Photo[]) => this.photos = photos);
  }
}
