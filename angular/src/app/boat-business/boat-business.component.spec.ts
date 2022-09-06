import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatBusinessComponent } from './boat-business.component';

describe('BoatBusinessComponent', () => {
  let component: BoatBusinessComponent;
  let fixture: ComponentFixture<BoatBusinessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatBusinessComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatBusinessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
