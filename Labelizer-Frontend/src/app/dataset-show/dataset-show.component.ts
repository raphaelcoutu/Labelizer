import { Component, OnInit, ViewChild } from '@angular/core';
import { DxTabsComponent } from 'devextreme-angular/ui/tabs';

@Component({
  selector: 'app-dataset-show',
  templateUrl: './dataset-show.component.html',
  styleUrls: ['./dataset-show.component.scss']
})
export class DatasetShowComponent implements OnInit {

  @ViewChild('tabs', {static: true}) tabs: DxTabsComponent;

  tabList: any[] = [
    {
      text: 'Photos',
    }, {
      text: 'Labels',
    }, {
      text: 'Upload',
    }
  ]

  constructor() { }

  ngOnInit() {
    this.tabs.selectedIndex = 0
  }
}
