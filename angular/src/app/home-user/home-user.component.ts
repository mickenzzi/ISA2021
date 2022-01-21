import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { Review } from '../model/review';
import { Complaint } from '../model/complaint';
import { Reservation } from '../model/reservation';
import { UserService } from '../service/user.service';
import { ReviewService } from '../service/review.service';
import { HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-home-user',
  templateUrl: './home-user.component.html',
  styleUrls: ['./home-user.component.css']
})
export class HomeUserComponent implements OnInit {
  id!: number;
  public reservations: Reservation[];
  review1: Review = new Review();
  complaint: Complaint  = new Complaint();
  user: User = new User();
  adventureId!: number;
  //show review parameter
  flag?: boolean;
  //show complaint parameter
  flag1?: boolean;
  
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private userService: UserService,
	private reviewService: ReviewService,
  ) {
	  this.reservations = [];
	  }

  ngOnInit(): void {
	this.id = this.route.snapshot.params['id'];
	this.getAllReservations();
	this.flag = false;
	this.flag1 = false;
	this.getUser();
  }
  public createReview(idAdventure1?: number): void{
	  this.flag = true;
	if(this.review1.comment === null || this.review1.comment === undefined || this.review1.comment.length === 0 || idAdventure1 === undefined)
	{
		alert('Unesite recenziju i ocenu recenzije');
	}
	else{
		this.review1.userReview = this.user;
		this.adventureId = idAdventure1;
	this.reviewService.createReview(this.review1,this.adventureId).subscribe(
		 (response) => {
			 this.flag = false;
			 this.review1.comment = undefined;
			 alert('Vas komentar ceka odobrenje');
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
	);
	}
  }
  
   public createComplaint(idAdventure1?: number): void{
	this.flag1=true;
	if(this.complaint.content === null || this.complaint.content === undefined || this.complaint.content.length === 0 || idAdventure1 === undefined)
	{
		alert('Unesite tekst zalbe.');
	}
	else{
		this.adventureId = idAdventure1;
		this.reviewService.createComplaint(this.complaint,this.id,this.adventureId).subscribe(
		 (response) => {
			 this.flag1 = false;
			 alert('Uspesno ste kreirali zalbu.');
			 this.complaint.content = undefined;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
	);
	}
  }
  
  public getAllReservations(): void{
		this.reviewService.getAllUserReservations(this.id).subscribe(
		 (response: Reservation[]) => {
			 this.reservations = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
	
	public getUser(): void{
		this.userService.getUser(this.id).subscribe(
		 (response: User) => {
			this.user = response;
			}
		);
	}

}
