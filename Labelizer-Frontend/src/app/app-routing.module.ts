import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LabelizerComponent } from './labelizer/labelizer.component';
import { HomeComponent } from './home/home.component';
import { DatasetListComponent } from './dataset-list/dataset-list.component';
import { DatasetShowComponent } from './dataset-show/dataset-show.component';


const routes: Routes = [
  {
    path: 'datasets',
    component: DatasetListComponent,
  },
  {
    path: 'datasets/:id',
    component: DatasetShowComponent,
  },
  {
    path: 'labelizer/:id',
    component: LabelizerComponent
  },
  {
    path: '**',
    redirectTo: 'datasets'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
