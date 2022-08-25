import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CalendarDayViewBeforeRenderEvent, CalendarEvent, CalendarMonthViewBeforeRenderEvent, CalendarView, CalendarWeekViewBeforeRenderEvent } from 'angular-calendar';
import { Subscription } from 'rxjs';
import { Cottage } from '../model/cottage';
import { Termin } from '../model/termin';
import { TerminCottage } from '../model/terminCottage';
import { User } from '../model/user';
import { CottageService } from '../service/cottage.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-cottage-calendar',
  templateUrl: './cottage-calendar.component.html',
  styleUrls: ['./cottage-calendar.component.css']
})
export class CottageCalendarComponent implements OnInit {

  //subscribe
  user: User = new User();
  //unsubsribe
  subs: Subscription[] = [];
  //local
  id!: number;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));
  cottage: Cottage = new Cottage();
  cottageId!: number;
  isOwner: boolean = false;
  title: String = "Termini";
  termin: TerminCottage = new TerminCottage;
  selectedTermin: TerminCottage = new TerminCottage;
  termins: TerminCottage[] = new Array<TerminCottage>();
  flagCreateTermin: boolean = false;
  flagEditTermin: boolean = false;
  fullName: String = "";

  //calendar
  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  activeDayIsOpen: boolean = true;
  CalendarView = CalendarView;

  
  constructor(private router: Router, private userService: UserService, private cottageService: CottageService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.cottageId = this.route.snapshot.params['id'];
    this.getCottage();
    //console.log(this.cottageId);
    this.getAllTermins();
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
    this.subs.push(this.cottageService.getCottage(this.cottageId).subscribe((response: Cottage) => {
      this.cottage = response;

    }));
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  back() {
    this.router.navigate(['/cottage/' + this.cottageId]);
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
      if(this.user.role === "ROLE_COTTAGE_OWNER"){
        this.isOwner = true;
      }
      if(this.isOwner){
        this.title = "Termini";
      } else {
        this.title = "Rezervisite termin";
      }
    }));
  }

  showCreateTermin() {
    this.flagCreateTermin = !this.flagCreateTermin;
  }

  hideEditTermin() {
    this.flagEditTermin = !this.flagEditTermin;
  }

  editTermin() {
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
        alert('Izmenili ste termin.');
        this.getAllTermins();
        this.flagEditTermin = false;
      }, (error: HttpErrorResponse) => {
        alert("Zeljeni termin je vec zauzet.");
      }));
      
    }
  }
    
  }

  showEditTermin(idTermin1?: number) {
    if (idTermin1 === undefined) { } else {
      this.subs.push(this.cottageService.getCottageTermin(idTermin1).subscribe((response: Termin) => {
        this.termin = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
      this.flagCreateTermin = false;
      this.flagEditTermin = true;
    }
  }

  createTermin() {
    if (this.user.id === undefined || this.termin.start === undefined || this.termin.end === undefined || this.termin.capacity === undefined) {
      alert("Popunite sva polja!");
    } else {
      if (new Date(this.termin.start ?? "") >= new Date(this.termin.end ?? "")) {
        alert("Datumi nisu validni.");
      }
      else if (this.termin.start.length !== 20 || this.termin.end.length !== 20) {
        alert("Nevalidan format datuma.");
      }
      else {
        this.subs.push(this.cottageService.createTermin(this.termin, this.cottageId).subscribe(() => {
          this.getAllTermins();
          this.termin = new TerminCottage();
          alert('Uspesno ste kreirali termin');
          this.flagCreateTermin = false;
          this.flagEditTermin = false;
        }, (error: HttpErrorResponse) => {
          alert("Termin vec postoji.")
        }));
      }
    }
  }

  getAllTermins() {
    if (this.cottageId === undefined) { }
    else {
      this.subs.push(this.cottageService.getCottageTermins(this.cottageId).subscribe((response) => {
        this.termins = response;
        console.log(this.termins);
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  beforeMonthViewRender(renderEvent: CalendarMonthViewBeforeRenderEvent): void {
    renderEvent.body.forEach((day) => {
      const dayOfMonth = day.date
      this.getAllTermins();
      for (let term of this.termins) {
        const start = new Date(term.start ?? "")
        start.setHours(0, 0, 0);
        const end = new Date(term.end ?? "")
        console.log(end);
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
    //this.getAllReservations()
    for (let term of this.termins) {
      const start = new Date(term.start ?? "")
      start.setHours(0, 0, 0);
      const end = new Date(term.end ?? "")
      if (date >= start && date <= end) {
        this.selectedTermin = term;
        this.showEditTermin(this.selectedTermin.id);
        break;
      }
    }

    /* for (let res of this.reservations) {
      const start = new Date(res.start ?? "")
      start.setHours(0, 0, 0);
      const end = new Date(res.end ?? "")
      if (date >= start && date <= end) {
        this.selectedReservation = res
        this.fullName = res.userReservation.firstName + " " + res.userReservation.lastName
        break;
      }
    } */

    if (this.selectedTermin.reserved) {
      this.flagEditTermin = true;
    }

    else {
      //this.editTerminShow(this.selectedTermin.id);
    }
  }
}
