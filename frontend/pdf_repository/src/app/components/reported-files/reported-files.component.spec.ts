import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReportedFilesComponent } from './reported-files.component';

describe('ReportedFilesComponent', () => {
  let component: ReportedFilesComponent;
  let fixture: ComponentFixture<ReportedFilesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReportedFilesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReportedFilesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
