import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatTableModule } from '@angular/material/table'

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HomeAdminComponent } from './home-admin/home-admin.component';
import { ProfileAdminComponent } from './profile-admin/profile-admin.component';
import { RegistrationComponent } from './registration/registration.component';
import { LoginComponent } from './login/login.component';
import { HomeUserComponent } from './home-user/home-user.component';
import { HomeGuestComponent } from './home-guest/home-guest.component';
import { HomeInstructorComponent } from './home-instructor/home-instructor.component';
import { HomeCottageOwnerComponent } from './home-cottage-owner/home-cottage-owner.component';
import { HomeBoatOwnerComponent } from './home-boat-owner/home-boat-owner.component';
import { RegistrationAdminComponent } from './registration-admin/registration-admin.component';
import { ProfileInstructorComponent } from './profile-instructor/profile-instructor.component';
import { AddAdventureComponent } from './add-adventure/add-adventure.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HomeAdminComponent,
    ProfileAdminComponent,
    RegistrationComponent,
    LoginComponent,
    HomeUserComponent,
    HomeGuestComponent,
    HomeInstructorComponent,
    HomeCottageOwnerComponent,
    HomeBoatOwnerComponent,
    RegistrationAdminComponent,
    ProfileInstructorComponent,
    AddAdventureComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NoopAnimationsModule,
	MatTableModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
