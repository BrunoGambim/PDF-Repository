import { TestBed } from '@angular/core/testing';

import { UpdateClientPasswordService } from './update-client-password.service';

describe('UpdateClientPasswordService', () => {
  let service: UpdateClientPasswordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateClientPasswordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
