import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluatePDFComponent } from './evaluate-pdf.component';

describe('EvaluatePDFComponent', () => {
  let component: EvaluatePDFComponent;
  let fixture: ComponentFixture<EvaluatePDFComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EvaluatePDFComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EvaluatePDFComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
