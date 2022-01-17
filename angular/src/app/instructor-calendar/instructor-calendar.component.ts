import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { Comment } from '../model/comment';
import { Termin } from '../model/termin';
import { Reservation } from '../model/reservation';
import { Adventure } from '../model/adventure';
import { UserService } from '../service/user.service';
import { ReviewService } from '../service/review.service';
import { AdventureService } from '../service/adventure.service';
import { HttpErrorResponse} from '@angular/common/http';
@Component({
  selector: 'app-instructor-calendar',
  templateUrl: './instructor-calendar.component.html',
  styleUrls: ['./instructor-calendar.component.css']
})
export class InstructorCalendarComponent implements OnInit {
  id!: number;
  idTermin: number=0;
  start: string = "";
  end: string = "";
  adventureId!: number;
  userId!: number;
  reservationId!: number;
  //termini
  flag1: boolean = false;
  //izmena termina
  flag2: boolean = false;
  //rezervacije
  flag3: boolean = false;
  //tekst komentara
  flag4: boolean = false;
  public termins: Termin[];
  public reservations: Reservation[]
  public termin: Termin = new Termin();
  public comment: Comment = new Comment();
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private userService: UserService,
	private reviewService: ReviewService,
	private adventureService: AdventureService,
  ) {
		this.termins = [];
		this.reservations = [];
	  }

  ngOnInit(): void {
	this.id = this.route.snapshot.params['id'];
	this.getAllTermins();
	this.getAllReservations();
	this.flag1=false;
	this.flag2=false;
	this.flag3=false;
	this.flag4=false;
  }
  
  goBack(): void{
	this.router.navigate(['/homeInstructor',this.id]);
  }
  
  public showTermin(): void{
	  if(this.flag1 === false){
		  this.flag1 = true;
		  this.flag3 = false;
		  this.getAllTermins();
	  }
	  else{
		  this.flag1 = false;
		  this.flag2 = false;
	  }
  }
  
   public showReservation(): void{
	  if(this.flag3 === false){
		  this.flag3 = true;
		  this.flag1 = false;
		  this.getAllReservations();
	  }
	  else{
		  this.flag3 = false;
		}
  }
  
  public deleteTermin(idTermin1?: number): void{
	if(idTermin1 === undefined){
		alert('Id ne postoji');
	}
	else{
		this.idTermin = idTermin1;
		this.adventureService.deleteTermin(this.idTermin).subscribe(
		 (response) => {
			alert('Uspesno ste izbrisali termin');
			this.getAllTermins();
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
  }
	
	public editTerminShow(idTermin1?: number): void{
	if(idTermin1 === undefined){
		alert('Id ne postoji');
	}
	else{
		this.idTermin = idTermin1;
		this.adventureService.getTermin(this.idTermin).subscribe(
		 (response: Termin) => {
			 this.termin = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
		this.flag2=true;
		}
	}
	
	public reject(): void{
		this.flag2 = false;
	}
	
	public editTermin(): void{
		this.adventureService.updateTermin(this.termin).subscribe(
			(response)=>{
				alert('Izmenili ste termin.');
				this.flag1 = false;
				this.getAllTermins();
			},
			(error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
  
   public getAllTermins(): void{
		this.adventureService.getAllTermins(this.id).subscribe(
		 (response: Termin[]) => {
			 this.termins = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
	
	 public getAllReservations(): void{
		this.adventureService.getAllReservation(this.id).subscribe(
		 (response: Reservation[]) => {
			 this.reservations = response;
			 console.log(this.reservations);
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
	
	public rejectReservation(reservationId1?: number): void{
		if(reservationId1 === undefined){
			alert('Neispravan id.');
		}
		else{
			this.reservationId = reservationId1;
			this.adventureService.deleteReservation(this.reservationId).subscribe(
				(response) => {
				alert('Uspesno ste izbrisali rezervaciju');
				this.getAllReservations();
				this.getAllTermins();
			},
			(error: HttpErrorResponse) =>{
				alert(error.message);
			}
			);
			
		}
	}
	
	public deleteReservationTermin(reservationId1?: number,start1?: string,end1?: string): void{
		if(reservationId1 === undefined){
			alert('Neispravan id.');
		}
		else{
			this.reservationId = reservationId1;
			if(start1 === undefined){
				alert('Neispravan datum pocetka.');
			}
			else{
				this.start = start1;
				if(end1 === undefined)
				{
					alert('Neispravan datum zavrsetka.');
				}
				else{
					this.end = end1;
					this.adventureService.deleteReservationTermin(this.reservationId,this.start,this.end).subscribe(
						(response) => {
							alert('Uspesno ste izbrisali rezervaciju i termin');
							this.getAllReservations();
							this.getAllTermins();
						},
						(error: HttpErrorResponse) =>{
						alert(error.message);
						}
					);
				}
			}
		}
	}
	
	public createComment(userId1?: number): void{
		this.flag4 = true;
		if(userId1 === undefined || this.comment.content === null || this.comment.content === undefined || this.comment.content.length === 0){
			alert('Unesite sadrzaj komentara.');
		}
		else{
			if(this.comment.negative === undefined){
				this.comment.negative = false;
			}
			this.userId = userId1;
			this.reviewService.createComment(this.comment,this.userId,this.id).subscribe(
			(response) => {
			this.flag4 = false;
			alert('Uspesno ste kreirali komentar');
			this.getAllReservations();
			this.getAllTermins();
			},
			(error: HttpErrorResponse) =>{
			alert(error.message);
			}
			);
			
		}
	}
	
	public createReservation(start1?: string,end1?: string,adventureId1?: number,userId1?: number): void{
		if(start1 === undefined){
			alert('Neispravan string');
		}
		else{
			this.start = start1;
			if(end1 === undefined){
				alert('Neispravan string');
			}
			else{
				this.end = end1;
				if(adventureId1 === undefined){
					alert('Neispravan id');
				}
				else{
					this.adventureId = adventureId1;
					if(userId1 === undefined)
					{
						alert('Neispravan id');
					}
					else{
						this.userId = userId1;
						this.adventureService.createReservation(this.start,this.end,this.adventureId,this.userId).subscribe(
							(response) => {
							alert('Potvrdili ste rezervaciju');
							this.getAllReservations();
							this.getAllTermins();
							},
							(error: HttpErrorResponse) =>{
							alert(error.message);
							}
						);
					}
				}
			}
		}
		
	}
	

}
