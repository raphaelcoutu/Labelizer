import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LabelizerComponent } from './labelizer.component';

describe('LabelizerComponent', () => {
  let component: LabelizerComponent;
  let fixture: ComponentFixture<LabelizerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LabelizerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LabelizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
