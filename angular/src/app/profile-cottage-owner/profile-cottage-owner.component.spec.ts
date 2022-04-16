import {ComponentFixture, TestBed} from '@angular/core/testing';
import { ProfileCottageOwnerComponent } from './profile-cottage-owner.component';

describe('ProfileCottageOwnerComponent', () => {
  let component: ProfileCottageOwnerComponent;
  let fixture: ComponentFixture<ProfileCottageOwnerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProfileCottageOwnerComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileCottageOwnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
