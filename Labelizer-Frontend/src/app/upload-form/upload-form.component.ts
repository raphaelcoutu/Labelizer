import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-upload-form',
  templateUrl: './upload-form.component.html',
  styleUrls: ['./upload-form.component.scss']
})
export class UploadFormComponent implements OnInit {

  uploadUrl = environment.apiUrl + '/datasets/1/upload';

  constructor() { }

  ngOnInit() {
  }

}
