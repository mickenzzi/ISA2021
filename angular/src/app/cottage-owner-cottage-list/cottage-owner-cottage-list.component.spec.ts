import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CottageOwnerCottageListComponent} from './cottage-owner-cottage-list.component';


describe('CottageOwnerCottageList', () => {
  let component: CottageOwnerCottageListComponent;
  let fixture: ComponentFixture<CottageOwnerCottageListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CottageOwnerCottageListComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageOwnerCottageListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
