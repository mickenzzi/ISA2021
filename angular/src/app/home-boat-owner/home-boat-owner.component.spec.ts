import {ComponentFixture, TestBed} from '@angular/core/testing';

import {HomeBoatOwnerComponent} from './home-boat-owner.component';

describe('HomeBoatOwnerComponent', () => {
  let component: HomeBoatOwnerComponent;
  let fixture: ComponentFixture<HomeBoatOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeBoatOwnerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeBoatOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
