import {ComponentFixture, TestBed} from '@angular/core/testing';
import { ProfileOwnerComponent } from './profile-owner.component';

describe('ProfileCottageOwnerComponent', () => {
  let component: ProfileOwnerComponent;
  let fixture: ComponentFixture<ProfileOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfileOwnerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
