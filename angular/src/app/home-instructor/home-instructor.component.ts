import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { Request } from '../model/request';
import { Adventure } from '../model/adventure';
import { UserService } from '../service/user.service';
import { RequestService } from '../service/request.service';
import { AdventureService } from '../service/adventure.service';
import { HttpErrorResponse} from '@angular/common/http';
@Component({
  selector: 'app-home-instructor',
  templateUrl: './home-instructor.component.html',
  styleUrls: ['./home-instructor.component.css']
})
export class HomeInstructorComponent implements OnInit {

  user: User = new User();
  user1: User = new User();
  public adventures: Adventure[];
  public requests: Request[];
  idAdventure: number=0;
  username: string = "";
  search: string = "";
  flagTitle?: boolean;
  flagPrice?: boolean;
  flagCapacity?: boolean;
  //show menu panel
  flag1?: boolean;
  //show request delete dialog
  flag2?: boolean;
  //is adventure reserved
  flag3?: boolean;
  URL_ss = "";
  URL: string = "";
  URL_R: string = ""
  URL_path : string = "/assets/img/";
  textRequest: string = "";
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser')); 
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private userService: UserService,
	private requestService: RequestService,
	private adventureService: AdventureService,
	) {
		this.adventures = [];
		this.requests = [];
	  }
  ngOnInit(): void {
	this.flag1 = false;
	this.flag2 = false;
	if(this.currentUser === null){
		alert('Niste se ulogovali');
		this.logOut();
	}
	else{
	this.getUser();
	}
  }	
  showHidden(){
	if(this.flag1 === false){
		this.flag1 = true;
	}
	else{
		this.flag1 = false;
	}
  }
  
  
  logOut(){
	localStorage.removeItem('currentUser');
	localStorage.clear();
	this.router.navigate(['/login']);
  }
  
  goToProfile(){
	this.router.navigate(['/profileInstructor']);
  }
  
  public requestDelete(): void{
	if(this.flag2 === false){
		this.flag2 = true;
		this.getAllAdventures();
	}
	else{
		this.flag2 = false;
	}
	this.flag1=false;
  }
  
  
  public approveRequest(): void{
	  if(this.user.id === undefined){}
	  else{
	  if(this.textRequest === undefined || this.textRequest === null || this.textRequest.length === 0){
		alert('Morate uneti razlog zahteva za brisanje naloga');
	  }
	  else{
		this.flag2=false;
	    this.getAllAdventures();
		this.requestService.createRequest(this.user.id,this.textRequest).subscribe(
			response =>{
			alert('Poslali ste zahtev za brisanje naloga')
			this.textRequest = "";
			}
		);
	  }
	  }
  }
  
  public rejectRequest(): void{
	  this.flag2=false;
	  this.getAllAdventures();
  }
  
  public getUser(): void{
		const username = this.currentUser.username1;
		this.userService.findUser(username).subscribe(
		 (response: User) => {
			 this.user = response;
			 this.getAllAdventures();
		 }
		);
	}
	
	public uploadImage(event: any): void{
		this.URL_ss = this.URL.substring(12);
		this.URL_R = this.URL_path + this.URL_ss;
		console.log(this.URL_R);
	}
	
	addAdventure(){
		this.router.navigate(['/addAdventure']);
	}
	
	public getAllAdventures(): void{
		if(this.user.id === undefined){}
		else{
		if(this.flag2 === false){
		this.adventureService.getAllAdventures(this.user.id).subscribe(
		 (response: Adventure[]) => {
			 this.adventures = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
		}
		else{
			this.adventures = [];
		}
		}
	}
	goToCalendar(): void{
		this.router.navigate(['/instructorCalendar']);
	}
	goToAdventure(idAdventure1?: number): void{
		if(idAdventure1 === undefined){
			alert('Id nije validan');
		}
		else{
			this.idAdventure = idAdventure1;
			this.router.navigate(['/homeAdventure',this.idAdventure]);
		}
	}
	
	deleteAdventure(idAdventure1?: number): void{
		if(idAdventure1 === undefined){
			alert('Id nije validan');
		}
		else{
		this.idAdventure = idAdventure1;
		this.adventureService.deleteAdventure(this.idAdventure).subscribe(
			(response) => {
				alert("Obrisali ste avanturu");
				this.getAllAdventures();
				}
			);
		}
	}
	
	searchAdventure(event: any): void{
		if(this.user.id === undefined){}
		else{
		if(this.search === null || this.search.length === 0){
			this.getAllAdventures();
		}
		else{
		this.adventureService.getSearchAdventures(this.user.id,this.search).subscribe(
		 (response: Adventure[]) => {
			 this.adventures = response;
		  },
		);
		}
		}
	}
	
	sortByTitle(): void{
		if(this.user.id === undefined){}
		else{
		if(this.flagTitle === false){
			this.flagTitle = true;
		}
		else{
			this.flagTitle = false;
		}
		this.adventureService.sortAdventuresByTitle(this.user.id, this.flagTitle).subscribe(
		 (response: Adventure[]) => {
			 this.adventures = response;
		  }
		);
		}
	}
	sortByPrice(): void{
		if(this.user.id === undefined){}
		else{
		if(this.flagPrice === false){
			this.flagPrice = true;
		}
		else{
			this.flagPrice = false;
		}
		this.adventureService.sortAdventuresByPrice(this.user.id, this.flagPrice).subscribe(
		 (response: Adventure[]) => {
			 this.adventures = response;
		  }
		);
		}
	}
	sortByCapacity(): void{
		if(this.user.id === undefined){}
		else{
		if(this.flagCapacity === false){
			this.flagCapacity = true;
		}
		else{
			this.flagCapacity = false;
		}
		
		this.adventureService.sortAdventuresByCapacity(this.user.id, this.flagCapacity).subscribe(
		 (response: Adventure[]) => {
			 this.adventures = response;
		  }
		);
		}
	}
}
