import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeAdminUsersComponent } from './home-admin-users.component';

describe('HomeAdminUsersComponent', () => {
  let component: HomeAdminUsersComponent;
  let fixture: ComponentFixture<HomeAdminUsersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeAdminUsersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeAdminUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
