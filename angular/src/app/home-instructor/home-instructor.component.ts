import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../model/user';
import {Request} from '../model/request';
import {Adventure} from '../model/adventure';
import {UserService} from '../service/user.service';
import {RequestService} from '../service/request.service';
import {AdventureService} from '../service/adventure.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Subscription} from "rxjs";
import { ChartType } from 'chart.js';

@Component({
  selector: 'app-home-instructor',
  templateUrl: './home-instructor.component.html',
  styleUrls: ['./home-instructor.component.css']
})
export class HomeInstructorComponent implements OnInit {
  //subscribe
  user: User = new User();
  user1: User = new User();
  adventures: Adventure[];
  requests: Request[];
  //unsubscribe
  subs: Subscription[] = [];
  //local
  idAdventure: number = 0;
  username: string = "";
  search: string = "";
  URL_ss = "";
  URL: string = "";
  URL_R: string = ""
  URL_path: string = "/assets/img/";
  textRequest: string = "";
  averageGrade = "0";
  startDate: string = "";
  endDate: string = "";
  profit: string = "";
  //flags
  flagTitle?: boolean;
  flagPrice?: boolean;
  flagCapacity?: boolean;
  //show menu panel
  flag1?: boolean;
  //show request delete dialog
  flag2?: boolean;
  //is adventure reserved
  flag3?: boolean;
  //prikaz avanutura
  flag4: boolean = true;
  //prikaz finansija
  flag5: boolean = false;
  //prikaz mesecnih rezervacija
  flag6: boolean = false;
  //prikaz sedmicnih rezervacija
  flag7: boolean = false;
  //prikaz dnevnih rezervacija
  flag8: boolean = false;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

   //chart1
   barChartLabels1: string[] = ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"];
   barChartData1: any[] = [];
   chartData1: any[] = [];
   barChartType1: ChartType = "bar";

    //chart2
  barChartLabels2: string[] = ["Prva", "Druga", "Treca", "Cetvrta", "Peta"];
  barChartData2: any[] = [];
  chartData2: any[] = [];
  barChartType2: ChartType = "bar";

   //chart3
   barChartLabels3: string[] = ["Ponedeljak", "Utorak", "Sreda", "Cetvrtak", "Petak", "Subota", "Nedelja"];
   barChartData3: any[] = [];
   chartData3: any[] = [];
   barChartType3: ChartType = "bar";

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private requestService: RequestService, private adventureService: AdventureService) {
    this.adventures = [];
    this.requests = [];
  }

  ngOnInit(): void {
    this.flag1 = false;
    this.flag2 = false;
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

  goToProfile() {
    this.router.navigate(['/profileInstructor']);
  }

  addAdventure() {
    this.router.navigate(['/addAdventure']);
  }

  goToAdventure(idAdventure1?: number) {
    if (idAdventure1 === undefined) {
      alert('Id nije validan');
    } else {
      this.idAdventure = idAdventure1;
      this.router.navigate(['/homeAdventure', this.idAdventure]);
    }
  }

  goToCalendar() {
    this.router.navigate(['/instructorCalendar']);
  }


  public uploadImage(event: any) {
    this.URL_ss = this.URL.substring(12);
    this.URL_R = this.URL_path + this.URL_ss;
  }

  showHidden() {
    if (this.flag1 === false) {
      this.flag1 = true;
    } else {
      this.flag1 = false;
    }
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
      this.getAllAdventures();
    }));
  }

  getAllAdventures() {
    if (this.user.id === undefined) {
    } else {
      if (this.flag2 === false) {
        this.subs.push(this.adventureService.getAllAdventures(this.user.id).subscribe((response: Adventure[]) => {
          this.adventures = response;
        }));
      } else {
        this.adventures = [];
      }
    }
  }


  requestDelete() {
    this.flag5 = false;
    if (this.flag2 === false) {
      this.flag2 = true;
      this.getAllAdventures();
    } else {
      this.flag2 = false;
    }
    this.flag1 = false;
  }


  approveRequest() {
    if (this.user.id === undefined) {
    } else {
      if (this.textRequest === undefined || this.textRequest === null || this.textRequest.length === 0) {
        alert('Morate uneti razlog zahteva za brisanje naloga');
      } else {
        this.flag2 = false;
        this.getAllAdventures();
        this.subs.push(this.requestService.createRequest(this.user.id, this.textRequest).subscribe(() => {
          alert('Poslali ste zahtev za brisanje naloga')
          this.textRequest = "";
        }));
      }
    }
  }

  rejectRequest() {
    this.flag2 = false;
    this.getAllAdventures();
  }

  reject() {
    this.flag5 = false;
    this.flag4 = true;
    this.flag6 = false;
    this.flag7 = false;
    this.flag8 = false;
  }

  showProfit() {
    this.subs.push(this.userService.getInstructorProfit(this.user.id ?? 0, this.startDate, this.endDate).subscribe((response: string) => {
      this.profit = response + " €"
    }, (error: HttpErrorResponse) => {
      alert("Datumi nisu uneti u ispravnom formatu.");
      this.startDate = "";
      this.endDate = "";
     }
    ));
  }


  deleteAdventure(idAdventure1?: number) {
    if (idAdventure1 === undefined) {
      alert('Id nije validan');
    } else {
      this.idAdventure = idAdventure1;
      this.subs.push(this.adventureService.deleteAdventure(this.idAdventure).subscribe(() => {
        alert("Obrisali ste avanturu");
        this.getAllAdventures();
      }));
    }
  }

  searchAdventure(event: any) {
    if (this.user.id === undefined) {
    } else {
      if (this.search === null || this.search.length === 0 || this.search === undefined) {
        this.getAllAdventures();
      } else {
        this.subs.push(this.adventureService.getSearchAdventures(this.user.id, this.search).subscribe((response: Adventure[]) => {
          this.adventures = response;
        }));
      }
    }
  }

  sortByTitle() {
    if (this.user.id === undefined) {
    } else {
      if (this.flagTitle === false) {
        this.flagTitle = true;
      } else {
        this.flagTitle = false;
      }
      this.subs.push(this.adventureService.sortAdventuresByTitle(this.user.id, this.flagTitle).subscribe((response: Adventure[]) => {
        this.adventures = response;
      }));
    }
  }

  sortByPrice() {
    if (this.user.id === undefined) {
    } else {
      if (this.flagPrice === false) {
        this.flagPrice = true;
      } else {
        this.flagPrice = false;
      }
      this.subs.push(this.adventureService.sortAdventuresByPrice(this.user.id, this.flagPrice).subscribe((response: Adventure[]) => {
        this.adventures = response;
      }));
    }
  }

  sortByCapacity() {
    if (this.user.id === undefined) {
    } else {
      if (this.flagCapacity === false) {
        this.flagCapacity = true;
      } else {
        this.flagCapacity = false;
      }

      this.subs.push(this.adventureService.sortAdventuresByCapacity(this.user.id, this.flagCapacity).subscribe((response: Adventure[]) => {
        this.adventures = response;
      }));
    }
  }

  sortByGrade() {
    if (this.user.id === undefined) {
    } else {
      if (this.flagPrice === false) {
        this.flagPrice = true;
      } else {
        this.flagPrice = false;
      }
      this.subs.push(this.adventureService.sortAdventuresByGrade(this.user.id, this.flagPrice).subscribe((response: Adventure[]) => {
        this.adventures = response;
      }));
    }
  }

  showFinancies(){
    this.flag4 = false;
    this.flag1 = false;
    this.flag5 = true;
    this.getReservationsPerMonth();
    this.getReservationsPerWeek();
    this.getReservationsPerDay();
  }

  getReservationsPerMonth() {
    this.subs.push(this.userService.getReservationsPerMonth(this.user.id ?? 0).subscribe((response) => {
      this.chartData1 = response;
      this.barChartData1 = [{data: this.chartData1, label: 'Broj rezervacija prikazan po mesecima'}]
    }));
  }

  getReservationsPerWeek() {
    this.subs.push(this.userService.getReservationsPerWeek(this.user.id ?? 0).subscribe((response) => {
      this.chartData2 = response;
      this.barChartData2 = [{data: this.chartData2, label: 'Broj rezervacija prikazan po sedmicama'}]
    }));
  }

  getReservationsPerDay() {
    this.subs.push(this.userService.getReservationsPerDay(this.user.id ?? 0).subscribe((response) => {
      this.chartData3 = response;
      this.barChartData3 = [{data: this.chartData3, label: 'Broj rezervacija prikazan po danima'}]
    }));
  }

  showMonthReservation() {
    this.flag6 = true;
    this.flag7 = false;
    this.flag8 = false;
  }

  showWeekReservation() {
    this.flag6 = false;
    this.flag7 = true;
    this.flag8 = false;
  }

  showDayReservation() {
    this.flag6 = false;
    this.flag7 = false;
    this.flag8 = true;
  }

}
