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
  //show review parameter
  flag?: boolean;
  //show complaint parameter
  flag1?: boolean;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private reviewService: ReviewService) {
    this.reservations = [];
  }

  ngOnInit(): void {
    this.getAllReservations();
    this.flag = false;
    this.flag1 = false;
    this.getUser();
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe())
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
    }));
  }


  getAllReservations() {
    if (this.user.id === undefined) {
    } else {
      this.subs.push(this.reviewService.getAllUserReservations(this.user.id).subscribe((response: Reservation[]) => {
        this.reservations = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  createReview(idAdventure1?: number) {
    this.flag = true;
    if (this.review1.comment === null || this.review1.comment === undefined || this.review1.comment.length === 0 || idAdventure1 === undefined) {
      alert('Unesite recenziju i ocenu recenzije');
    } else {
      this.review1.userReview = this.user;
      this.adventureId = idAdventure1;
      this.subs.push(this.reviewService.createReview(this.review1, this.adventureId).subscribe(() => {
        this.flag = false;
        this.review1.comment = undefined;
        alert('Vas komentar ceka odobrenje');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  createComplaint(idAdventure1?: number) {
    this.flag1 = true;
    if (this.complaint.content === null || this.complaint.content === undefined || this.complaint.content.length === 0 || idAdventure1 === undefined || this.user.id === undefined) {
      alert('Unesite tekst zalbe.');
    } else {
      this.adventureId = idAdventure1;
      this.subs.push(this.reviewService.createComplaint(this.complaint, this.user.id, this.adventureId).subscribe(() => {
        this.flag1 = false;
        alert('Uspesno ste kreirali zalbu.');
        this.complaint.content = undefined;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

}
