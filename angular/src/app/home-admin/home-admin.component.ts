import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { Review } from '../model/review';
import { Comment } from '../model/comment';
import { Request } from '../model/request';
import { UserService } from '../service/user.service';
import { ReviewService } from '../service/review.service';
import { RequestService } from '../service/request.service';
import { HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-home-admin',
  templateUrl: './home-admin.component.html',
  styleUrls: ['./home-admin.component.css']
})
export class HomeAdminComponent implements OnInit {  
  user: User = new User();
  user1: User = new User();
  public requests: Request[];
  public reviews: Review[];
  public comments: Comment[];
  id!: number;
  username: string = "";
  rejectText?: string;
  title: string = "";
  idRequest: number = 0;
  reviewId!: number;
  commentId!: number;
  //show menu panel
  flag1?: boolean;
  //show notifications panel
  flag2?: boolean;
  //show reject panel
  flag3?: boolean;
  //show reviews panel
  flag4?: boolean;
  //show comments panel
  flag5?: boolean;
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private userService: UserService,
	private requestService: RequestService,
	private reviewService: ReviewService,
	) {
		this.requests = [];
		this.reviews = [];
		this.comments = [];
	  }
  ngOnInit(): void {
	this.flag1 = false;
	this.flag2 = false;
	this.flag3 = false;
	this.flag4 = false;
	this.flag5 = false;
	this.id = this.route.snapshot.params['id'];
	this.getUser();
	this.getAllRequest();
	this.getAllReviews();
	this.getAllComments();
  }	
  showHidden(){
	if(this.flag1 === false){
		this.flag1 = true;
	}
	else{
		this.flag1 = false;
	}
  }
  
  showComments(){
	this.flag1 = false;
	this.flag2 = false;
	this.flag4 = false;
	this.flag5 = true;
  }
  
  showNotifications(){
	this.flag1 = false;
	this.flag2 = true;
	this.flag4 = false;
	this.flag5 = false;
  }
  showReviews(){
	this.flag1 = false;
	this.flag2 = false;
	this.flag4 = true;
	this.flag5 = false;
  }
  
  closeNotification(){
	this.flag2 = false;
	this.flag4 = false;
	this.flag5 = false;
  }
  
  redirectAdminRegistration(){
	this.router.navigate(['/registrationAdmin', this.id]);
  }
  
  logOut(){
	this.router.navigate(['/login']);
  }
  
  goToProfile(){
	this.router.navigate(['/profileAdmin', this.id]);
  }
  
  goToHomeAdminUsers(){
	this.router.navigate(['/homeAdminUsers',this.id]);
  }
  
  public getUser(): void{
		this.userService.getUser(this.id).subscribe(
		 (response: User) => {
			 this.user = response;
			 if(this.user.firstTimeLogged === true){
				alert('Korisnik se loguje prvi put,neophodno je da potvrdi lozinku');
				this.router.navigate(['/profileAdmin', this.id]);
			 }
		 }
		);
	}
	
  public getAllRequest(): void{
		this.requestService.getAllRequest(this.id).subscribe(
		 (response: Request[]) => {
			 this.requests = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
	

	public getAllReviews(): void{
		this.reviewService.getAllReviews().subscribe(
		 (response: Review[]) => {
			 this.reviews = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
	
	public getAllComments(): void{
		this.reviewService.getAllComments().subscribe(
		 (response: Comment[]) => {
			 this.comments = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
	
	public enableReview(reviewId1?: number): void{
		if(reviewId1 === undefined){
			alert('Neispravan id');
		}
		else{
			this.reviewId = reviewId1;
			this.reviewService.enableReview(this.reviewId).subscribe(
			(response) => {
				this.getAllReviews();
				alert('Validirali ste recenziju');
			}	,
			(error: HttpErrorResponse) =>{
			  alert(error.message);
			}
			);
		}
	}
	
	public enableComment(commentId1?: number): void{
		if(commentId1 === undefined){
			alert('Neispravan id');
		}
		else{
			this.commentId = commentId1;
			this.reviewService.enableComment(this.commentId).subscribe(
			(response) => {
				this.getAllComments();
				alert('Validirali ste komentar');
			}	,
			(error: HttpErrorResponse) =>{
			  alert(error.message);
			}
			);
		}
	}
	public deleteComment(commentId1?: number): void{
		if(commentId1 === undefined){
			alert('Neispravan id');
		}
		else{
			this.commentId = commentId1;
			this.reviewService.deleteComment(this.commentId).subscribe(
			(response) => {
				this.getAllComments();
				alert('Obrisali ste komentar');
			}	,
			(error: HttpErrorResponse) =>{
			  alert(error.message);
			}
			);
		}
	}
	public disableReview(reviewId1?: number): void{
		if(reviewId1 === undefined){
			alert('Neispravan id');
		}
		else{
			this.reviewId = reviewId1;
			this.reviewService.deleteReview(this.reviewId).subscribe(
			(response) => {
				this.getAllReviews();
				alert('Komentar nije prosao validaciju');
			}	,
			(error: HttpErrorResponse) =>{
			  alert(error.message);
			}
			);
		}
	}
	
	public deleteReview(reviewId1?: number): void{
		if(reviewId1 === undefined){
			alert('Neispravan id');
		}
		else{
			this.reviewId = reviewId1;
			this.reviewService.deleteReview(this.reviewId).subscribe(
			(response) => {
				this.getAllReviews();
				alert('Obrisali ste komentar');
			}	,
			(error: HttpErrorResponse) =>{
			  alert(error.message);
			}
			);
		}
	}
	
	
  public enableUser(username1?: string, title1?: string, idRequest1?: number):void{
	  if(username1 === undefined){
		alert("Korisnicko ime ne postoji");
	  }
	  else{
	  this.username = username1;
	  if(title1 === undefined){
		 alert("Naslov nije validan.");
	  }
	  else{
		this.title = title1;
		if(idRequest1 === undefined){
			 alert("Id nije validan.");
		}
		else{
		this.idRequest = idRequest1;
		if(this.title.includes("brisanje")){
			this.flag3 = true;
			if(this.rejectText === undefined || this.rejectText === null || this.rejectText.length === 0){
				alert('Unos razloga odbijanja je obavezan');
			}
			else{
			this.userService.approveDeleteRequest(this.username, this.idRequest, this.rejectText).subscribe(
			(response: User) => {
				alert("Nalog je obrisan");
				this.user1 = response;
				this.getAllRequest();
				this.flag3 = false;
				}
			);
			}
		}
		else{
			this.userService.enableUser(this.username, this.idRequest).subscribe(
			(response: User) => {
				alert("Nalog je verifikovan");
				this.user1 = response;
				this.getAllRequest();
				this.flag3 = false;
				}
			);
		}
		}
	  }
	}
  }
  
  public disableUser(username1?: string, title1?: string, idRequest1?: number):void{
	  if(username1 === undefined){
		alert("Korisnicko ime ne postoji");
	  }
	  else{
	  this.username = username1;
	  if(title1 === undefined){
		 alert("Naslov nije validan.");
	  }
	  else{
		this.title = title1;
		if(idRequest1 === undefined){
			 alert("Id nije validan.");
		}
		else{
		this.idRequest = idRequest1;
		if(this.title.includes("brisanje")){
			this.flag3 = true;
			if(this.rejectText === undefined || this.rejectText === null || this.rejectText.length === 0){
				alert('Unos razloga odbijanja je obavezan');
			}
			else{
			this.userService.rejectDeleteRequest(this.username, this.idRequest, this.rejectText).subscribe(
			(response: User) => {
				alert("Nije odobreno brisanje naloga");
				this.flag3 = false;
				this.user1 = response;
				this.getAllRequest();
				}
			);
			}
		}
		else{
			this.flag3 = true;
			if(this.rejectText === undefined || this.rejectText === null || this.rejectText.length === 0){
				alert('Unos razloga odbijanja je obavezan');
			}
			else{
				this.userService.disableUser(this.username, this.idRequest, this.rejectText).subscribe(
				(response: User) => {
					alert("Nalog nije verifikovan.");
					this.flag3 = false;
					this.user1 = response;
					this.getAllRequest();
					}
				);
			}
		}
		}
	  }
	}
  }
}
