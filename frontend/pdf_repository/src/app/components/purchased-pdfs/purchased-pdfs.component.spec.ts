import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchasedPDFsComponent } from './purchased-pdfs.component';

describe('PurchasedPDFsComponent', () => {
  let component: PurchasedPDFsComponent;
  let fixture: ComponentFixture<PurchasedPDFsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PurchasedPDFsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PurchasedPDFsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
