import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeAdventureComponent } from './home-adventure.component';

describe('HomeAdventureComponent', () => {
  let component: HomeAdventureComponent;
  let fixture: ComponentFixture<HomeAdventureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeAdventureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeAdventureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
