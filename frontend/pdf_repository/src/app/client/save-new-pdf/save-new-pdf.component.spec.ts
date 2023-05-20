import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveNewPDFComponent } from './save-new-pdf.component';

describe('SaveNewPDFComponent', () => {
  let component: SaveNewPDFComponent;
  let fixture: ComponentFixture<SaveNewPDFComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SaveNewPDFComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaveNewPDFComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
