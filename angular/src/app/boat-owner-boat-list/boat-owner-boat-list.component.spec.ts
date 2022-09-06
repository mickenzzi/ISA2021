import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatOwnerBoatListComponent } from './boat-owner-boat-list.component';

describe('BoatOwnerBoatListComponent', () => {
  let component: BoatOwnerBoatListComponent;
  let fixture: ComponentFixture<BoatOwnerBoatListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatOwnerBoatListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatOwnerBoatListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
