import { TestBed } from '@angular/core/testing';

import { UpdateUserPasswordService } from './update-user-password.service';

describe('UpdateUserPasswordService', () => {
  let service: UpdateUserPasswordService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UpdateUserPasswordService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
