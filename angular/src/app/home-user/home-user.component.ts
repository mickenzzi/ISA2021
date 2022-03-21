import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../model/user';
import {Review} from '../model/review';
import {Complaint} from '../model/complaint';
import {Reservation} from '../model/reservation';
import {UserService} from '../service/user.service';
import {ReviewService} from '../service/review.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-home-user', templateUrl: './home-user.component.html', styleUrls: ['./home-user.component.css']
})
export class HomeUserComponent implements OnInit {
  //subscribe
  reservations: Reservation[];
  review1: Review = new Review();
  complaint: Complaint = new Complaint();
  user: User = new User();
  //unsubscribe
  subs: Subscription[] = [];
  //local
  adventureId!: number;
  //flags
  //show review
  flag?: boolean;
  flagButton? : boolean = true;
  //show complaint
  flag1?: boolean;
  flag1Button?: boolean = true;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private reviewService: ReviewService) {
    this.reservations = [];
  }

  ngOnInit(): void {
    this.flag = false;
    this.flag1 = false;
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

  closeModal() {
    this.flag = false;
    this.flag1 = false;
    this.flagButton = true;
    this.flag1Button = true;
    this.getAllReservations();
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
      this.getAllReservations();
    }));
  }

  getSingleUserReservation(reservationId: number) {
    this.subs.push(this.reviewService.getSingleUserReservation(reservationId).subscribe((response) => {
      this.reservations = response;
    }));
  }

  getAllReservations() {
    if (this.user.id != null) {
      this.subs.push(this.reviewService.getAllUserReservations(this.user.id).subscribe((response) => {
        this.reservations = response;
        this.flagButton = true;
        this.flag1Button = true;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }

  }

  createReview(idAdventure1?: number, reservationId?: number) {
    this.flag = true;
    this.flagButton = true;
    this.flag1Button = false;
    if (this.review1.comment === null || this.review1.comment === undefined || this.review1.comment.length === 0 || idAdventure1 === undefined) {
      alert('Unesite recenziju i ocenu recenzije');
      if (reservationId === undefined) {
      } else {
        this.getSingleUserReservation(reservationId);
      }
    } else {
      this.review1.userReview = this.user;
      this.adventureId = idAdventure1;
      this.subs.push(this.reviewService.createReview(this.review1, this.adventureId).subscribe(() => {
        this.flag = false;
        this.review1.comment = undefined;
        this.getAllReservations();
        alert('Vas komentar ceka odobrenje');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  createComplaint(idAdventure1?: number, reservationId?: number) {
    this.flag1 = true;
    this.flagButton = false;
    this.flag1Button = true;
    if (this.complaint.content === null || this.complaint.content === undefined || this.complaint.content.length === 0 || idAdventure1 === undefined || this.user.id === undefined) {
      alert('Unesite tekst žalbe.');
      if(reservationId === undefined){}
      else {
        this.getSingleUserReservation(reservationId);
      }
    } else {
      this.adventureId = idAdventure1;
      this.subs.push(this.reviewService.createComplaint(this.complaint, this.user.id, this.adventureId).subscribe(() => {
        this.flag1 = false;
        alert('Uspesno ste kreirali zalbu.');
        this.complaint.content = undefined;
        this.getAllReservations();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

}
