import {ComponentFixture, TestBed} from '@angular/core/testing';

import {HomeCottageComponent} from './home-cottage.component';

describe('HomeCottageOwnerComponent', () => {
  let component: HomeCottageComponent;
  let fixture: ComponentFixture<HomeCottageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HomeCottageComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeCottageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
