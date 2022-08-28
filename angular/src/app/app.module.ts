import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS, } from '@angular/common/http';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table'
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
import { ProfileOwnerComponent } from './profile-owner/profile-owner.component';
import { AddAdventureComponent } from './add-adventure/add-adventure.component';
import { HomeAdventureComponent } from './home-adventure/home-adventure.component';
import { HomeAdminUsersComponent } from './home-admin-users/home-admin-users.component';
import { InstructorCalendarComponent } from "./instructor-calendar/instructor-calendar.component";
import { NgChartsModule } from 'ng2-charts';
import { AgmCoreModule } from '@agm/core';
import { ScheduleModule, DayService, WeekService, WorkWeekService, MonthService, MonthAgendaService } from '@syncfusion/ej2-angular-schedule';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { authInterceptorProviders } from './helpers/token-interceptor';
import { CottageOwnerCottageListComponent } from './cottage-owner-cottage-list/cottage-owner-cottage-list.component';
import { AddCottageComponent } from './add-cottage/add-cottage.component';
import { HomeCottageComponent } from './home-cottage/home-cottage.component';
import { CottageCalendarComponent } from './cottage-calendar/cottage-calendar.component';

import { MatGridListModule } from '@angular/material/grid-list';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule} from '@angular/material/menu'
import { MatListModule } from '@angular/material/list'
import { MatToolbarModule} from '@angular/material/toolbar'
import { MatIconModule} from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input'
import { MatCardModule} from '@angular/material/card';
import { MatDatepickerModule } from '@angular/material/datepicker';
import {MatRadioModule} from '@angular/material/radio';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatTabsModule} from '@angular/material/tabs';
import { CottageBusinessComponent } from './cottage-business/cottage-business.component';


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
    AddAdventureComponent,
    HomeAdventureComponent,
    HomeAdminUsersComponent,
    InstructorCalendarComponent,
    ProfileOwnerComponent,
    CottageOwnerCottageListComponent,
    AddCottageComponent,
    HomeCottageComponent,
    CottageCalendarComponent,
    CottageBusinessComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    NoopAnimationsModule,
    MatTableModule,
    FormsModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    NgChartsModule,
    MatGridListModule,
    MatButtonModule,
    MatMenuModule,
    MatListModule,
    MatToolbarModule,
    MatIconModule,
    MatInputModule,
    MatCardModule,
    MatDatepickerModule,
    MatRadioModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    AgmCoreModule.forRoot({ apiKey: 'AIzaSyC9guE6bI9x1oAsg63x2CKSN0AfKPgWhr0', libraries: ['places'] }),
    ScheduleModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    })
  ],
  providers: [DayService, WeekService, WorkWeekService, MonthAgendaService, MonthService, authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
