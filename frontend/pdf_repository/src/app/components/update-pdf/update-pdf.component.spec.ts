import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatePDFComponent } from './update-pdf.component';

describe('UpdatePDFComponent', () => {
  let component: UpdatePDFComponent;
  let fixture: ComponentFixture<UpdatePDFComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdatePDFComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdatePDFComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
