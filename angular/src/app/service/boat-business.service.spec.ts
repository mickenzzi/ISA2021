import { TestBed } from '@angular/core/testing';

import { BoatBusinessService } from './boat-business.service';

describe('BoatBusinessService', () => {
  let service: BoatBusinessService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoatBusinessService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
