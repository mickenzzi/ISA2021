import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeBoatComponent } from './home-boat.component';

describe('HomeBoatComponent', () => {
  let component: HomeBoatComponent;
  let fixture: ComponentFixture<HomeBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
