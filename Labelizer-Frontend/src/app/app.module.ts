import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LabelizerComponent } from './labelizer/labelizer.component';
import { HomeComponent } from './home/home.component';
import { DatasetListComponent } from './dataset-list/dataset-list.component';
import { DatasetShowComponent } from './dataset-show/dataset-show.component';
import { LabelListComponent } from './label-list/label-list.component';
import { PhotoListComponent } from './photo-list/photo-list.component';
import { UploadFormComponent } from './upload-form/upload-form.component';

import { DxButtonModule } from 'devextreme-angular/ui/button';
import { DxSelectBoxModule } from 'devextreme-angular/ui/select-box';
import { DxDataGridModule } from 'devextreme-angular/ui/data-grid';
import { DxTabsModule } from 'devextreme-angular/ui/tabs';
import { DxFileUploaderModule } from 'devextreme-angular/ui/file-uploader';
import { DxPopupModule } from 'devextreme-angular/ui/popup';
import { DxFormModule } from 'devextreme-angular/ui/form';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LabelizerComponent,
    HomeComponent,
    DatasetListComponent,
    DatasetShowComponent,
    LabelListComponent,
    PhotoListComponent,
    UploadFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    DxButtonModule,
    DxSelectBoxModule,
    DxDataGridModule,
    DxTabsModule,
    DxFileUploaderModule,
    DxPopupModule,
    DxFormModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
