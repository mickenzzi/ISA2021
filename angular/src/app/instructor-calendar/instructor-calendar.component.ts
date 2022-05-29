import { ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { Comment } from '../model/comment';
import { Termin } from '../model/termin';
import { Reservation } from '../model/reservation';
import { UserService } from '../service/user.service';
import { ReviewService } from '../service/review.service';
import { AdventureService } from '../service/adventure.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Subscription } from "rxjs";
import { 
  CalendarEvent, 
  CalendarMonthViewBeforeRenderEvent,
  CalendarWeekViewBeforeRenderEvent,
  CalendarDayViewBeforeRenderEvent,
  CalendarView } from 'angular-calendar';
import { HttpClient } from '@angular/common/http';
import { startOfYear, subYears } from 'date-fns';
import { TouchSequence } from 'selenium-webdriver';


const colors: any = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF',
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
};

@Component({
  selector: 'app-instructor-calendar',
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './instructor-calendar.component.html',
  styleUrls: ['./instructor-calendar.component.css']
})
export class InstructorCalendarComponent implements OnInit {
  //subsribe
  termins: Termin[] = new Array<Termin>();
  reservations: Reservation[]
  termin: Termin = new Termin();
  termin1: Termin = new Termin();
  comment: Comment = new Comment();
  user: User = new User();
  selectedTermin: Termin = new Termin();
  //unsubscribe
  subs: Subscription[] = [];
  //local
  idTermin: number = 0;
  start: string = "";
  end: string = "";
  adventureId!: number;
  userId!: number;
  reservationId!: number;
  //flags
  flag1: boolean = false;
  //izmena termina
  flag2: boolean = false;
  //rezervacije
  flag3: boolean = false;
  //tekst komentara
  flag4: boolean = false;
  //modal za dodavanje termina
  flag5: boolean = false;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  view: CalendarView = CalendarView.Month;

  viewDate: Date = new Date();

  events: CalendarEvent[] = [];

  activeDayIsOpen: boolean = true;

  CalendarView = CalendarView;

  
  beforeMonthViewRender(renderEvent: CalendarMonthViewBeforeRenderEvent): void {
    renderEvent.body.forEach((day) => {
      const dayOfMonth = day.date
      this.getAllTermins();
      for(let term of this.termins){
        const start = new Date(term.start ?? "")
        const end = new Date(term.end ?? "")
        if (dayOfMonth >= start && dayOfMonth <= end) {
          if(term.reserved === true){
          day.cssClass = 'bg-red';
          }
          else {
            day.cssClass = 'bg-blue';
          }
        }
      }
    });
  }

  constructor(private userService: UserService, private reviewService: ReviewService, private adventureService: AdventureService, private router: Router) {
    this.reservations = [];
  }

  changeDay(date: Date) {
    this.getAllTermins();
    for(let term of this.termins){
      const start = new Date(term.start ?? "")
      const end = new Date(term.end ?? "")
      if(date >= start && date <= end){
          this.selectedTermin = term;
          break;
      }
    }

    this.editTerminShow(this.selectedTermin.id);
    this.selectedTermin = new Termin();
  }

  ngOnInit(): void {
    this.flag1 = false;
    this.flag2 = false;
    this.flag3 = false;
    this.flag4 = false;
    this.flag5 = false;
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

  goBack() {
    this.router.navigate(['/homeInstructor']);
  }

  public showTermin() {
    if (!this.flag1) {
      this.flag1 = true;
      this.flag2 = false;
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.getAllTermins();
    } else {
      this.flag1 = false;
      this.flag2 = false;
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
    }
  }

  public showReservation() {
    if (!this.flag3) {
      this.flag3 = true;
      this.flag1 = false;
      this.flag2 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.getAllReservations();
    } else {
      this.flag1 = false;
      this.flag2 = false;
      this.flag4 = false;
      this.flag5 = false;
    }
  }

  reject() {
    this.flag2 = false;
    this.flag5 = false
  }

  showAddTermin() {
    this.flag5 = true;
    this.flag1 = false;
  }

  public closeComment(): void {
    this.flag4 = false;
    this.getAllReservations();
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
      this.getAllTermins();
      this.getAllReservations();
    }));
  }

  getAllTermins() {
    if (this.user.id === undefined) { }
    else {
      this.subs.push(this.adventureService.getAllTermins(this.user.id).subscribe((response) => {
        this.termins = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  getAllReservations() { 
    if (this.user.id === undefined) {
    } else {
      this.subs.push(this.adventureService.getAllReservation(this.user.id).subscribe((response) => {
        this.reservations = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  createTermin() {
    if (this.user.id === undefined) {
    } else {
      this.subs.push(this.adventureService.createTermin(this.termin1, this.user.id).subscribe(() => {
        this.flag5 = false;
        this.flag1 = true;
        this.getAllTermins();
        this.termin1 = new Termin();
      }, (error: HttpErrorResponse) => {
        alert("Termin vec postoji.")
      }));
    }
  }

  deleteTermin() {
      this.subs.push(this.adventureService.deleteTermin(this.termin.id ?? 0).subscribe(() => {
        alert('Uspesno ste izbrisali termin');
        this.getAllTermins();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
      this.flag2 = false;
  }


  editTerminShow(idTermin1?: number) {
    if (idTermin1 === undefined) {} else {
      this.idTermin = idTermin1;
      this.subs.push(this.adventureService.getTermin(this.idTermin).subscribe((response: Termin) => {
        this.termin = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
      this.flag2 = true;
      this.flag1 = false;
      this.flag5 = false;
    }
  }

  editTermin() {
    this.subs.push(this.adventureService.updateTermin(this.termin).subscribe(() => {
      alert('Izmenili ste termin.');
      this.getAllTermins();
    }, (error: HttpErrorResponse) => {
      alert("Zeljeni termin je vec zauzet.");
    }));
    this.flag2 = false;
    this.flag5 = false; 
  }

  createReservation(start1?: string, end1?: string, adventureId1?: number, userId1?: number) {
    if (start1 === undefined) {
      alert('Neispravan string');
    } else {
      this.start = start1;
      if (end1 === undefined) {
        alert('Neispravan string');
      } else {
        this.end = end1;
        if (adventureId1 === undefined) {
          alert('Neispravan id');
        } else {
          this.adventureId = adventureId1;
          if (userId1 === undefined) {
            alert('Neispravan id');
          } else {
            this.userId = userId1;
            this.subs.push(this.adventureService.createReservation(this.start, this.end, this.adventureId, this.userId).subscribe(() => {
              alert('Potvrdili ste rezervaciju');
              this.getAllReservations();
              this.getAllTermins();
            }, (error: HttpErrorResponse) => {
              alert(error.message);
            }));
          }
        }
      }
    }
  }


  rejectReservation(reservationId1?: number) {
    if (reservationId1 === undefined) {
      alert('Neispravan id.');
    } else {
      this.reservationId = reservationId1;
      this.subs.push(this.adventureService.deleteReservation(this.reservationId).subscribe(() => {
        alert('Uspesno ste izbrisali rezervaciju');
        this.getAllReservations();
        this.getAllTermins();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));

    }
  }

  deleteReservationTermin(reservationId1?: number, start1?: string, end1?: string) {
    if (reservationId1 === undefined) {
      alert('Neispravan id.');
    } else {
      this.reservationId = reservationId1;
      if (start1 === undefined) {
        alert('Neispravan datum pocetka.');
      } else {
        this.start = start1;
        if (end1 === undefined) {
          alert('Neispravan datum zavrsetka.');
        } else {
          this.end = end1;
          this.subs.push(this.adventureService.deleteReservationTermin(this.reservationId, this.start, this.end).subscribe(() => {
            alert('Uspesno ste izbrisali rezervaciju i termin');
            this.getAllReservations();
            this.getAllTermins();
          }, (error: HttpErrorResponse) => {
            alert(error.message);
          }));
        }
      }
    }
  }

  createComment(userId1?: number, reservationId?: number) {
    if (reservationId === undefined) {
    } else {
      this.subs.push(this.adventureService.getReservation(reservationId).subscribe((response) => {
        this.reservations = response;
      }));
    }
    if (this.user.id === undefined) {
    } else {
      this.flag4 = true;
      if (userId1 === undefined || this.comment.content === null || this.comment.content === undefined || this.comment.content.length === 0) {
        alert('Unesite sadrzaj komentara.');
      } else {
        if (this.comment.negative === undefined) {
          this.comment.negative = false;
        }
        this.userId = userId1;
        this.subs.push(this.reviewService.createComment(this.comment, this.userId, this.user.id).subscribe(() => {
          this.flag4 = false;
          alert('Uspesno ste kreirali komentar');
          this.getAllReservations();
          this.getAllTermins();
          this.comment.content = undefined;
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }));
      }
    }
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }


}
