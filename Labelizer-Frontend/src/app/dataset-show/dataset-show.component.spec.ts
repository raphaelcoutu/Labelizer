import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DatasetShowComponent } from './dataset-show.component';

describe('DatasetShowComponent', () => {
  let component: DatasetShowComponent;
  let fixture: ComponentFixture<DatasetShowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DatasetShowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DatasetShowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
