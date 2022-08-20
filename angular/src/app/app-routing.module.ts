import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {HomeComponent} from './home/home.component';
import {HomeAdminComponent} from './home-admin/home-admin.component';
import {ProfileAdminComponent} from './profile-admin/profile-admin.component';
import {RegistrationComponent} from './registration/registration.component';
import {RegistrationAdminComponent} from './registration-admin/registration-admin.component';
import {LoginComponent} from './login/login.component';
import {HomeUserComponent} from './home-user/home-user.component';
import {HomeGuestComponent} from './home-guest/home-guest.component';
import {HomeInstructorComponent} from './home-instructor/home-instructor.component';
import {HomeCottageOwnerComponent} from './home-cottage-owner/home-cottage-owner.component';
import {HomeBoatOwnerComponent} from './home-boat-owner/home-boat-owner.component';
import {ProfileInstructorComponent} from './profile-instructor/profile-instructor.component';
import {AddAdventureComponent} from './add-adventure/add-adventure.component';
import {HomeAdventureComponent} from './home-adventure/home-adventure.component';
import {HomeAdminUsersComponent} from './home-admin-users/home-admin-users.component';
import {InstructorCalendarComponent} from './instructor-calendar/instructor-calendar.component';
import { CottageOwnerCottageListComponent } from './cottage-owner-cottage-list/cottage-owner-cottage-list.component';
import { ProfileOwnerComponent } from './profile-owner/profile-owner.component';
import { AddCottageComponent } from './add-cottage/add-cottage.component';
import { HomeCottageComponent } from './home-cottage/home-cottage.component';
import { ProfileUserComponent } from './profile-user/profile-user.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'homeAdmin',
    component: HomeAdminComponent,
  },
  {
    path: 'registration',
    component: RegistrationComponent,
  },
  {
    path: 'profileAdmin',
    component: ProfileAdminComponent,
  },
  {
    path: 'profileInstructor',
    component: ProfileInstructorComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'homeUser',
    component: HomeUserComponent,
  },
  {
    path: 'homeInstructor',
    component: HomeInstructorComponent,
  },
  {
    path: 'homeGuest',
    component: HomeGuestComponent,
  },
  {
    path: 'homeCottageOwner',
    component: HomeCottageOwnerComponent,
  },
  {
    path: 'cottageList',
    component: CottageOwnerCottageListComponent,
  },
  {
    path: 'homeBoatOwner',
    component: HomeBoatOwnerComponent,
  },
  {
    path: 'registrationAdmin',
    component: RegistrationAdminComponent,
  },
  {
    path: 'addAdventure',
    component: AddAdventureComponent,
  },
  {
    path: 'homeAdventure/:idAdventure',
    component: HomeAdventureComponent,
  },
  {
    path: 'homeAdminUsers',
    component: HomeAdminUsersComponent,
  },
  {
    path: 'instructorCalendar',
    component: InstructorCalendarComponent,
  },
  {
    path: 'profileOwner',
    component: ProfileOwnerComponent,
  },
  {
    path: 'cottage',
    component: AddCottageComponent,
  },
  {
    path: 'cottage/:id',
    component: HomeCottageComponent,
  },
  {
    path: 'profileUser',
    component: ProfileUserComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
