import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatTableModule} from '@angular/material/table'
import {HomeAdminComponent} from './home-admin/home-admin.component';
import {ProfileAdminComponent} from './profile-admin/profile-admin.component';
import {RegistrationComponent} from './registration/registration.component';
import {LoginComponent} from './login/login.component';
import {HomeUserComponent} from './home-user/home-user.component';
import {HomeGuestComponent} from './home-guest/home-guest.component';
import {HomeInstructorComponent} from './home-instructor/home-instructor.component';
import {HomeCottageOwnerComponent} from './home-cottage-owner/home-cottage-owner.component';
import {HomeBoatOwnerComponent} from './home-boat-owner/home-boat-owner.component';
import {RegistrationAdminComponent} from './registration-admin/registration-admin.component';
import {ProfileInstructorComponent} from './profile-instructor/profile-instructor.component';
import { ProfileCottageOwnerComponent } from './profile-cottage-owner/profile-cottage-owner.component';
import {AddAdventureComponent} from './add-adventure/add-adventure.component';
import {HomeAdventureComponent} from './home-adventure/home-adventure.component';
import {HomeAdminUsersComponent} from './home-admin-users/home-admin-users.component';
import {InstructorCalendarComponent} from "./instructor-calendar/instructor-calendar.component";
import { CottageOwnerCottageListComponent } from './cottage-owner-cottage-list/cottage-owner-cottage-list.component';
import {NgChartsModule} from "ng2-charts";
import { AgmCoreModule } from '@agm/core';


@NgModule({
  declarations: [AppComponent, HomeComponent, HomeAdminComponent, ProfileAdminComponent, RegistrationComponent, LoginComponent, HomeUserComponent, HomeGuestComponent, HomeInstructorComponent, HomeCottageOwnerComponent, HomeBoatOwnerComponent, RegistrationAdminComponent, ProfileInstructorComponent, ProfileCottageOwnerComponent, AddAdventureComponent, HomeAdventureComponent, HomeAdminUsersComponent, InstructorCalendarComponent, CottageOwnerCottageListComponent],
  imports: [BrowserModule, HttpClientModule, AppRoutingModule, NoopAnimationsModule, MatTableModule, FormsModule, ReactiveFormsModule, NgChartsModule, AgmCoreModule.forRoot({apiKey: 'AIzaSyC9guE6bI9x1oAsg63x2CKSN0AfKPgWhr0', libraries: ['places']})],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
