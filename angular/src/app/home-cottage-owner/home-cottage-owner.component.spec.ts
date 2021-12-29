import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeCottageOwnerComponent } from './home-cottage-owner.component';

describe('HomeCottageOwnerComponent', () => {
  let component: HomeCottageOwnerComponent;
  let fixture: ComponentFixture<HomeCottageOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeCottageOwnerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeCottageOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
