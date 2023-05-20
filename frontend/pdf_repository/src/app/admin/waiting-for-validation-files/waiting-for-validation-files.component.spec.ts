import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaitingForValidationFilesComponent } from './waiting-for-validation-files.component';

describe('WaitingForValidationFilesComponent', () => {
  let component: WaitingForValidationFilesComponent;
  let fixture: ComponentFixture<WaitingForValidationFilesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WaitingForValidationFilesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WaitingForValidationFilesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
