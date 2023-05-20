import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnedPDFsComponent } from './owned-pdfs.component';

describe('OwnedPDFsComponent', () => {
  let component: OwnedPDFsComponent;
  let fixture: ComponentFixture<OwnedPDFsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnedPDFsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OwnedPDFsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
