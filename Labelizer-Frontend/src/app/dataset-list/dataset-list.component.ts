import { Component, OnInit } from '@angular/core';
import { Dataset } from '../_models/dataset';
import { DatasetService } from '../_services/dataset.service';

@Component({
  selector: 'app-dataset-list',
  templateUrl: './dataset-list.component.html',
  styleUrls: ['./dataset-list.component.scss']
})
export class DatasetListComponent implements OnInit {

  datasets: Dataset[] = [];

  constructor(private datasetService: DatasetService) { }

  ngOnInit() {
    this.datasetService.getAll().subscribe((datasets: Dataset[]) => this.datasets = datasets);
  }

}
