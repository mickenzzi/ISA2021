import { HttpErrorResponse } from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { ChartType } from 'chart.js';
import { Subscription } from 'rxjs';
import { User } from '../model/user';
import { BoatBusinessService } from '../service/boat-business.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-home-boat-owner',
  templateUrl: './home-boat-owner.component.html',
  styleUrls: ['./home-boat-owner.component.css']
})
export class HomeBoatOwnerComponent implements OnInit {

  //subscribe
  user: User = new User();
  //unsubsribe
  subs: Subscription[] = [];
  //local
  id!: number;
  //@ts-ignore
 currentUser = JSON.parse(localStorage.getItem('currentUser'));

 showBusiness: boolean = false;
 startDate: string = "";
 endDate: string = "";
 profit: string = "0";
 flagMonth: boolean = false;
 flagWeek: boolean = false;
 flagDay: boolean = false;

 chartType: ChartType = "bar";
 //Month
 monthlyLabels: string[] = ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"];

 monthlyChart1: any[] = [];
 reservationsMonthly: any[] = [];
 monthlyChart2: any[] = [];
 reservedDaysMonthly: any[] = [];
 monthlyChart3: any[] = [];
 profitMontly: any[] = [];

  //Week
 weeklyLabels: string[] = ["Prva", "Druga", "Treca", "Cetvrta", "Peta"];

 weeklyChart1: any[] = [];
 reservationsWeekly: any[] = [];
 weeklyChart2: any[] = [];
 reservedDaysWeekly: any[] = [];
 weeklyChart3: any[] = [];
 profitWeekly: any[] = [];

 //Day
 dailyLabels: string[] = ["Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja"];

 dailyChart1: any[] = [];
 reservationsDaily: any[] = [];
 dailyChart2: any[] = [];
 reservedDays: any[] = [];
 dailyChart3: any[] = [];
 profitDaily: any[] = [];


 constructor(private router: Router, private userService: UserService, private businessService: BoatBusinessService) {
 }

 ngOnInit(): void {
   if (this.currentUser === null) {
     alert('Niste se ulogovali');
     this.logOut();
   } else {
     this.getUser();
   }
 }

 ngOnDestroy() {
   this.subs.forEach(sub => sub.unsubscribe())
 }

 logOut() {
   localStorage.removeItem('currentUser');
   localStorage.clear();
   this.router.navigate(['/login']);
 }

 getUser() {
   const username = this.currentUser.username1;
   this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
     this.user = response;
   }));
 }

 goToCottageList() {
   this.router.navigate(['/boatList']);
 }

 goToProfile() {
   this.router.navigate(['/profileOwner']);
 }

 toggleBusiness() {
   this.showBusiness = !this.showBusiness;
   this.getReservationsPerMonth();
   this.getReservationsPerWeek();
   this.getReservationsPerDay();
 }

 getReservationsPerMonth() {
   this.subs.push(this.businessService.getReservationsPerMonth(this.user.id ?? 0).subscribe((response) => {
     this.reservationsMonthly = response;
     this.monthlyChart1 = [{data: this.reservationsMonthly, label: 'Broj rezervacija prikazan po mesecima'}]
   }));
   this.subs.push(this.businessService.getReservedDaysPerMonth(this.user.id ?? 0).subscribe((response) => {
     this.reservedDaysMonthly = response;
     this.monthlyChart2 = [{data: this.reservedDaysMonthly, label: 'Broj rezervisanih dana po mesecima'}]
   }));
   this.subs.push(this.businessService.getMonthlyProfit(this.user.id ?? 0).subscribe((response) => {
     this.profitMontly = response;
     this.monthlyChart3 = [{data: this.profitMontly, label: 'Profit prikazan po mesecima'}]
   }));
 }

 getReservationsPerWeek() {
   this.subs.push(this.businessService.getReservationsPerWeek(this.user.id ?? 0).subscribe((response) => {
     this.reservationsWeekly = response;
     this.weeklyChart1 = [{data: this.reservationsWeekly, label: 'Broj rezervacija prikazan po nedeljama u mesecu'}]
   }));
   this.subs.push(this.businessService.getReservedDaysPerWeek(this.user.id ?? 0).subscribe((response) => {
     this.reservedDaysWeekly = response;
     this.weeklyChart2 = [{data: this.reservedDaysWeekly, label: 'Broj rezervisanih dana po nedeljama u mesecu'}]
   }));
   this.subs.push(this.businessService.getWeeklyProfit(this.user.id ?? 0).subscribe((response) => {
     this.profitWeekly = response;
     this.weeklyChart3 = [{data: this.profitWeekly, label: 'Profit prikazan po nedeljama u mesecu'}]
   }));
 }

 getReservationsPerDay() {
   this.subs.push(this.businessService.getReservationsPerDay(this.user.id ?? 0).subscribe((response) => {
     this.reservationsDaily = response;
     this.dailyChart1 = [{data: this.reservationsDaily, label: 'Broj rezervacija na odredjen dan u nedelji'}]
   }));
   this.subs.push(this.businessService.getReservedDays(this.user.id ?? 0).subscribe((response) => {
     this.reservedDays = response;
     this.dailyChart2 = [{data: this.reservedDays, label: 'Broj rezervisanih dana u nedelji'}]
   }));
   this.subs.push(this.businessService.getDailyProfit(this.user.id ?? 0).subscribe((response) => {
     this.profitDaily = response;
     this.dailyChart3 = [{data: this.profitDaily, label: 'Profit prikazan po danima u nedelji'}]
   }));
 }

 showProfit() {
   this.subs.push(this.businessService.getProfit(this.user.id ?? 0, this.startDate, this.endDate).subscribe((response: number) => {
     this.profit = response + " €"
   }, (error: HttpErrorResponse) => {
     alert("Datumi nisu uneti u ispravnom formatu.");
     this.startDate = "";
     this.endDate = "";
    }
   ));
 }

 showMontlyBusiness() {
   this.flagMonth = true;
   this.flagWeek = false;
   this.flagDay = false;
 }

 showWeeklyBusiness() {
   this.flagMonth = false;
   this.flagWeek = true;
   this.flagDay = false;
 }

 showDailyBusiness() {
   this.flagMonth = false;
   this.flagWeek = false;
   this.flagDay = true;
 }
}
