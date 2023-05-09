import { TestBed } from '@angular/core/testing';

import { UpdatePDFService } from './update-pdf.service';

describe('UpdatePDFService', () => {
  let service: UpdatePDFService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdatePDFService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
