import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CalendarDayViewBeforeRenderEvent, CalendarEvent, CalendarMonthViewBeforeRenderEvent, CalendarView, CalendarWeekViewBeforeRenderEvent } from 'angular-calendar';
import { Subscription } from 'rxjs';
import { Boat } from '../model/boat';
import { Report } from '../model/report';
import { TerminBoat } from '../model/terminBoat';
import { User } from '../model/user';
import { BoatService } from '../service/boat.service';
import { ReportService } from '../service/report.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-boat-calendar',
  templateUrl: './boat-calendar.component.html',
  styleUrls: ['./boat-calendar.component.css']
})
export class BoatCalendarComponent implements OnInit {

  //subscribe
  user: User = new User();
  //unsubsribe
  subs: Subscription[] = [];
  //local
  id!: number;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));
  cottage: Boat = new Boat();
  cottageId!: number;
  isOwner: boolean = false;
  isUser: boolean = false;
  title: String = "Termini";
  termin: TerminBoat = new TerminBoat;
  selectedTermin: TerminBoat = new TerminBoat;
  termins: TerminBoat[] = new Array<TerminBoat>();
  report: Report = new Report();
  finishedReservations: TerminBoat[] = [];
  existingUser: User = new User();

  flagCreateTermin: boolean = false;
  flagEditTermin: boolean = false;
  flagShowCalendar: boolean = true;
  disableEditTermin: boolean = false;
  fullName: String = "";
  flagReserveTermin: boolean = false;
  flagReport: boolean = false;
  flagUserProfile: boolean = false;
  flagCreateUserTermin: boolean = false;


  //calendar
  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  activeDayIsOpen: boolean = true;
  CalendarView = CalendarView;

  
  constructor(private router: Router, private userService: UserService, private cottageService: BoatService, private route: ActivatedRoute, private reportService: ReportService) {
  }

  ngOnInit(): void {
    this.cottageId = this.route.snapshot.params['id'];
    this.getCottage();
    this.flagShowCalendar = false;
    this.showCalendar();
    //this.refresh();
    if (this.currentUser === null) {
    } else {
      this.getUser();
      //dodati if proveru za isDisabled, ako je zauzeta vikendica ili nije trenutni korisnik VLASNIK vikendice, isDisabled = true
    }
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe())
  }

  getCottage() {
    this.subs.push(this.cottageService.getCottage(this.cottageId).subscribe((response: Boat) => {
      this.cottage = response;
      this.getAllTermins();
      this.getFinishedTermins();
    }));
  }

  openCreateNewTermin(){
    this.flagCreateUserTermin = true;
    this.flagUserProfile = false;
    this.flagCreateTermin = false;
    this.flagEditTermin = false;
    this.flagShowCalendar = false;
    this.flagReserveTermin = false;
    this.flagReport = false;
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  back() {
    this.router.navigate(['/boat/' + this.cottageId]);
  }

  showProfile(user?: User){
    if(user != undefined){
      this.existingUser = user;
    }
    this.flagUserProfile = !this.flagUserProfile;
    this.flagCreateUserTermin = false;
    this.flagCreateTermin = false;
    this.flagEditTermin = false;
    this.flagShowCalendar = false;
    this.flagReserveTermin = false;
    this.flagReport = false;
  }

  closeProfile(){
    this.flagUserProfile = false;
    this.flagShowCalendar = true;
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
      if(this.user.role === "ROLE_BOAT_OWNER"){
        this.isOwner = true;
      } else if (this.user.role === "ROLE_USER"){
        this.isUser = true;
      }
      if(this.isOwner){
        this.title = "Termini";
      } else {
        this.title = "Rezervisite termin";
      }
    }));
  }



  showCreateTermin() {
    this.flagCreateTermin = true;
    this.flagEditTermin = false;
    this.flagShowCalendar = false;
    this.flagReserveTermin = false;
    this.flagReport = false;
  }

  showEditTermin() {
    this.flagEditTermin = true;
    this.flagCreateTermin = false;
    this.flagShowCalendar = false;
    this.flagReserveTermin = false;
    this.flagReport = false;
  }

  showCalendar(){ 
    this.flagEditTermin = false;
    this.flagCreateTermin = false;
    this.flagShowCalendar = true;
    this.flagReserveTermin = false;
    this.flagReport = false;
    setTimeout(() => { this.refresh(); }, 500);
  }

  showReport(term: TerminBoat){
    this.report.term = term;
    this.report.missedTerm = false;
    this.flagReport = true;
    this.flagEditTermin = false;
    this.flagCreateTermin = false;
    this.flagShowCalendar = false;
    this.flagReserveTermin = false;

  }

  sendReport(){
    if(this.report.missedTerm){
      this.report.comment = "Korisnik je propustio termin, dobija 1 instant penal";
      this.report.sanctioned = true;
      this.report.approved = true;
    } else {
      this.report.approved = false;
    }
    this.report.owner = this.user;
    console.log("Sankcije: " + this.report.sanctioned + " approved: " + this.report.approved + " missed term: " + this.report.missedTerm)
    this.subs.push(this.reportService.create(this.report).subscribe(() => {
      //this.report = new Report();
      window.location.reload();
      alert("Uspesno te poslali izvestaj");
    }, (error: HttpErrorResponse) => {
      alert("Greska pri popunjavanju izvestaja");
    }));
  }
  
  editTermin() {
    if(!this.disableEditTermin){
      if (this.user.id === undefined || this.selectedTermin.start === undefined || this.selectedTermin.end === undefined || this.selectedTermin.capacity === undefined) {
        alert("Popunite sva polja!");
      } else {
        if (new Date(this.selectedTermin.start ?? "") >= new Date(this.selectedTermin.end ?? "")) {
          alert("Datumi nisu validni.");
        }
        else if (this.selectedTermin.start.length !== 20 || this.selectedTermin.end.length !== 20) {
          alert("Nevalidan format datuma.");
        }
        else {
        this.subs.push(this.cottageService.updateCottageTermin(this.selectedTermin).subscribe(() => {
          this.getAllTermins();
          alert('Termin je izmenjen.')
        }, (error: HttpErrorResponse) => {
          alert("Zeljeni termin je vec zauzet.");
        }));

        //setTimeout(() => { this.refresh(); }, 100)
        this.showCalendar();
        }
      }
    }
    
  }

  openEditTermin(idTermin1?: number) {
    if (idTermin1 === undefined) { } else {
      this.subs.push(this.cottageService.getCottageTermin(idTermin1).subscribe((response: TerminBoat) => {
        this.selectedTermin = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
      this.showEditTermin();
    }
  }

  close(){
    window.location.reload();
  }

  createReservation() {
    if (this.termin.start === undefined || this.termin.end === undefined) {
      alert("Popunite sva polja!");
    } else {
      if (new Date(this.termin.start ?? "") >= new Date(this.termin.end ?? "")) {
        alert("Datumi nisu validni.");
      }
      else if (this.termin.start.length !== 20 || this.termin.end.length !== 20) {
        alert("Nevalidan format datuma.");
      }
      else {
        this.termin.reserved = true;
        this.termin.capacity = 1;
        this.termin.userReserved = this.existingUser;
        this.subs.push(this.cottageService.createTermin(this.termin, this.cottageId, 2).subscribe(() => {
          this.getAllTermins();
          
          window.location.reload();
          this.termin = new TerminBoat();
          alert("Uspesno te dodali termin");
        }, (error: HttpErrorResponse) => {
          alert("Termin vec postoji.");
        }));

        this.showCalendar();
      }
    }
  }

  createTermin() {
    if (this.termin.start === undefined || this.termin.end === undefined || this.termin.capacity === undefined) {
      alert("Popunite sva polja!");
    } else {
      if (new Date(this.termin.start ?? "") >= new Date(this.termin.end ?? "")) {
        alert("Datumi nisu validni.");
      }
      else if (this.termin.start.length !== 20 || this.termin.end.length !== 20) {
        alert("Nevalidan format datuma.");
      }
      else {
        this.subs.push(this.cottageService.createTermin(this.termin, this.cottageId, 1).subscribe(() => {
          this.getAllTermins();
          this.termin = new TerminBoat();
          alert("Uspesno te dodali termin");
        }, (error: HttpErrorResponse) => {
          alert("Termin vec postoji.");
        }));

        this.showCalendar();
      }
    }
  }

  getFinishedTermins() {
      this.subs.push(this.cottageService.getFinishedTermins(this.cottageId).subscribe((response: TerminBoat[]) => {
        this.finishedReservations = response;
        console.log("Called all termins")
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    
  }

  getAllTermins() {
      this.subs.push(this.cottageService.getCottageTermins(this.cottageId).subscribe((response: TerminBoat[]) => {
        this.termins = response;
        console.log("Called all termins")
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    
  }

  beforeMonthViewRender(renderEvent: CalendarMonthViewBeforeRenderEvent): void {
    this.getAllTermins();
    renderEvent.body.forEach((day) => {
      const dayOfMonth = day.date
      for (let term of this.termins) {
        const start = new Date(term.start ?? "")
        start.setHours(0, 0, 0);
        const end = new Date(term.end ?? "")
        if ((dayOfMonth >= start && dayOfMonth <= end && dayOfMonth.getFullYear() >= start.getFullYear() && dayOfMonth.getFullYear() <= end.getFullYear())) {
          if (term.reserved === true) {
            day.cssClass = 'bg-red';
          }
          else {
            if (term.action === true) {
              day.cssClass = 'bg-green';
            }
            else {
              day.cssClass = 'bg-blue';
            }
          }
        }
      }
    });
  }

  beforeWeekViewRender(renderEvent: CalendarWeekViewBeforeRenderEvent) {
    renderEvent.hourColumns.forEach((hourColumn) => {
      hourColumn.hours.forEach((hour) => {
        hour.segments.forEach((segment) => {
          for (let term of this.termins) {
            const start = new Date(term.start ?? "")
            const end = new Date(term.end ?? "")
            if (segment.date.getDate() >= start.getDate() && segment.date.getDate() <= end.getDate() && segment.date.getMonth() >= start.getMonth() && segment.date.getMonth() <= end.getMonth() && segment.date.getFullYear() >= start.getFullYear() && segment.date.getFullYear() <= end.getFullYear()) {
              if (segment.date.getHours() >= start.getHours() && segment.date.getHours() <= end.getHours() && start.getDate() === end.getDate()) {
                if (term.reserved === true) {
                  segment.cssClass = 'bg-red';
                }
                else {
                  if (term.action === true) {
                    segment.cssClass = 'bg-green';
                  }
                  else {
                    segment.cssClass = 'bg-blue';
                  }
                }
              }
              else if (segment.date.getDate() === start.getDate() && segment.date.getDate() + 1 <= end.getDate() && segment.date.getHours() >= start.getHours() && segment.date.getHours() <= 20) {
                if (term.reserved === true) {
                  segment.cssClass = 'bg-red';
                }
                else {
                  if (term.action === true) {
                    segment.cssClass = 'bg-green';
                  }
                  else {
                    segment.cssClass = 'bg-blue';
                  }
                }
              }
              else if (segment.date.getDate() > start.getDate() && segment.date.getDate() + 1 <= end.getDate() && segment.date.getHours() >= 8 && segment.date.getHours() <= 20) {
                if (term.reserved === true) {
                  segment.cssClass = 'bg-red';
                }
                else {
                  if (term.action === true) {
                    segment.cssClass = 'bg-green';
                  }
                  else {
                    segment.cssClass = 'bg-blue';
                  }
                }
              }
              else if (segment.date.getDate() === end.getDate() && segment.date.getHours() >= 8 && segment.date.getHours() <= end.getHours()) {
                if (term.reserved === true) {
                  segment.cssClass = 'bg-red';
                }
                else {
                  if (term.action === true) {
                    segment.cssClass = 'bg-green';
                  }
                  else {
                    segment.cssClass = 'bg-blue';
                  }
                }
              }
            }
          }
        });
      });
    });
  }

  beforeDayViewRender(renderEvent: CalendarDayViewBeforeRenderEvent) {
    renderEvent.hourColumns.forEach((hourColumn) => {
      hourColumn.hours.forEach((hour) => {
        hour.segments.forEach((segment) => {
          for (let term of this.termins) {
            const start = new Date(term.start ?? "")
            const end = new Date(term.end ?? "")
            if (segment.date.getDate() >= start.getDate() && segment.date.getDate() <= end.getDate() && segment.date.getFullYear() >= start.getFullYear() && segment.date.getFullYear() <= end.getFullYear() && segment.date.getMonth() >= start.getMonth() && segment.date.getMonth() <= end.getMonth()) {
              if (start.getDate() === end.getDate() && segment.date.getHours() >= start.getHours() && segment.date.getHours() <= end.getHours()) {
                if (term.reserved === true) {
                  segment.cssClass = 'bg-red';
                }
                else {
                  if (term.action === true) {
                    segment.cssClass = 'bg-green';
                  } else {
                    segment.cssClass = 'bg-blue';
                  }
                }
              }
              else if (segment.date.getDate() === start.getDate() && segment.date.getDate() + 1 <= end.getDate() && segment.date.getHours() >= start.getHours() && segment.date.getHours() <= 20) {
                if (term.reserved === true) {
                  segment.cssClass = 'bg-red';
                }
                else {
                  if (term.action === true) {
                    segment.cssClass = 'bg-green';
                  }
                  else {
                    segment.cssClass = 'bg-blue';
                  }
                }
              }
              else if (segment.date.getDate() > start.getDate() && segment.date.getDate() + 1 <= end.getDate() && segment.date.getHours() >= 8 && segment.date.getHours() <= 20) {
                if (term.reserved === true) {
                  segment.cssClass = 'bg-red';
                }
                else {
                  if (term.action === true) {
                    segment.cssClass = 'bg-green';
                  }
                  else {
                    segment.cssClass = 'bg-blue';
                  }
                }
              }
              else if (segment.date.getDate() === end.getDate() && segment.date.getHours() >= 8 && segment.date.getHours() <= end.getHours()) {
                if (term.reserved === true) {
                  segment.cssClass = 'bg-red';
                }
                else {
                  if (term.action === true) {
                    segment.cssClass = 'bg-green';
                  }
                  else {
                    segment.cssClass = 'bg-blue';
                  }
                }
              }
            }
          }
        });
      });
    });
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  changeDay(date: Date) {
    this.getAllTermins();
    if(this.isOwner){
      for (let term of this.termins) {
        const start = new Date(term.start ?? "")
        start.setHours(0, 0, 0);
        const end = new Date(term.end ?? "")
        if (date >= start && date <= end) {
          this.selectedTermin = term;
          this.openEditTermin(this.selectedTermin.id);
          break;
        }
      }

      if (this.selectedTermin.reserved) {
        this.disableEditTermin = true;
      } else {
        this.disableEditTermin = false;
      }

    } else if (this.isUser){
      if(this.selectedTermin.reserved && this.selectedTermin.userReserved?.id != this.user.id){
        alert("Termin je zauzet");
      } else {
        for (let term of this.termins) {
          const start = new Date(term.start ?? "")
          start.setHours(0, 0, 0);
          const end = new Date(term.end ?? "")
          if (date >= start && date <= end) {
            this.selectedTermin = term;
            this.openReserveTermin(this.selectedTermin.id);
            break;
          }
        }
      }
      
    } else {
      alert("Morate biti ulogovani da biste pristupili terminu!");
    }
  }
  
  openReserveTermin(idTermin1?: number) {
    if (idTermin1 === undefined) { } else {
      this.subs.push(this.cottageService.getCottageTermin(idTermin1).subscribe((response: TerminBoat) => {
        this.selectedTermin = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
      this.showReserveTermin();
    }
  }

  cancelReservation(){
    this.subs.push(this.cottageService.cancelReservation(this.selectedTermin).subscribe(() => {
      this.getAllTermins();
      alert('Uspesno ste otkazali rezervaciju.')
    }, (error: HttpErrorResponse) => {
      alert("Neuspesna rezervacija.");
    }));

    this.showCalendar();
  }

  showReserveTermin(){
    this.flagReserveTermin = !this.flagReserveTermin;
    this.flagEditTermin = false;
    this.flagCreateTermin = false;
    this.flagShowCalendar = false;
  }

  reserveTermin() {
    this.selectedTermin.userReserved = this.user;
    this.subs.push(this.cottageService.reserveTermin(this.selectedTermin).subscribe(() => {
      this.getAllTermins();
      console.log("User reserved: " + this.selectedTermin.userReserved?.id);
      alert('Uspesno ste rezervisali termin.')
    }, (error: HttpErrorResponse) => {
      alert("Neuspesna rezervacija.");
    }));

    this.showCalendar();
  }

  refresh(){
    let naredni: HTMLButtonElement = document.getElementById('Naredni') as HTMLButtonElement;
    setTimeout(() => { naredni.click() }, 100)
    let prethodni: HTMLButtonElement = document.getElementById('Prethodni') as HTMLButtonElement;
    setTimeout(() => { prethodni.click() }, 100)
  }

}
