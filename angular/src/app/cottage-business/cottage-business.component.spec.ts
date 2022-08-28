import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageBusinessComponent } from './cottage-business.component';

describe('CottageBusinessComponent', () => {
  let component: CottageBusinessComponent;
  let fixture: ComponentFixture<CottageBusinessComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageBusinessComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageBusinessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
