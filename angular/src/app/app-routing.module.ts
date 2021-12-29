import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './home/home.component';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { ProfileAdminComponent } from './profile-admin/profile-admin.component';
import { RegistrationComponent } from './registration/registration.component';
import { RegistrationAdminComponent } from './registration-admin/registration-admin.component';
import { LoginComponent } from './login/login.component';
import { HomeUserComponent } from './home-user/home-user.component';
import { HomeGuestComponent } from './home-guest/home-guest.component';
import { HomeInstructorComponent } from './home-instructor/home-instructor.component';
import { HomeCottageOwnerComponent } from './home-cottage-owner/home-cottage-owner.component';
import { HomeBoatOwnerComponent } from './home-boat-owner/home-boat-owner.component';

const routes: Routes = [
	{
	path: '',
	component: HomeComponent,
	pathMatch: 'full'
	},
	{
	path: 'homeAdmin/:id',
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
	path: 'login',
	component: LoginComponent,
	},
	{
	path: 'homeUser/:id',
	component: HomeUserComponent,
	},
	{
	path: 'homeInstructor/:id',
	component: HomeInstructorComponent,
	},
	{
	path: 'homeGuest',
	component: HomeGuestComponent,
	},
	{
	path: 'homeCottageOwner/:id',
	component: HomeCottageOwnerComponent,
	},
	{
	path: 'homeBoatOwner/:id',
	component: HomeBoatOwnerComponent,
	},
	{
	path: 'registrationAdmin/:id',
	component: RegistrationAdminComponent,
	}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
