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
  id!: number;
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
	this.id = this.route.snapshot.params['id'];
	this.getUser();
	this.getAllAdventures();
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
	this.router.navigate(['/login']);
  }
  
  goToProfile(){
	this.router.navigate(['/profileInstructor', this.id]);
  }
  
  public requestDelete(): void{
	if(this.flag2 === false){
		this.flag2 = true;
	}
	else{
		this.flag2 = false;
	}
	this.flag1=false;
  }
  
  
  public approveRequest(): void{
	  this.flag2=false;
	  this.requestService.createRequest(this.id).subscribe(
		response =>
		alert('Poslali ste zahtev za brisanje naloga')
	  );
	  
  }
  
  public rejectRequest(): void{
	  this.flag2=false;
  }
  
  public getUser(): void{
		this.userService.getUser(this.id).subscribe(
		 (response: User) => {
			 this.user = response;
		 }
		);
	}
	
	public uploadImage(event: any): void{
		this.URL_ss = this.URL.substring(12);
		this.URL_R = this.URL_path + this.URL_ss;
		console.log(this.URL_R);
	}
	
	addAdventure(){
		this.router.navigate(['/addAdventure', this.id]);
	}
	
	public getAllAdventures(): void{
		this.adventureService.getAllAdventures(this.id).subscribe(
		 (response: Adventure[]) => {
			 this.adventures = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
	goToCalendar(): void{
		this.router.navigate(['/instructorCalendar', this.id]);
	}
	goToAdventure(idAdventure1?: number): void{
		if(idAdventure1 === undefined){
			alert('Id nije validan');
		}
		else{
			this.idAdventure = idAdventure1;
			this.router.navigate(['/homeAdventure',this.id,this.idAdventure]);
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
				alert("Obrisana avantura");
				this.getAllAdventures();
				}
			);
		}
	}
	
	searchAdventure(event: any): void{
		if(this.search === null || this.search.length === 0){
			this.getAllAdventures();
		}
		else{
		this.adventureService.getSearchAdventures(this.id,this.search).subscribe(
		 (response: Adventure[]) => {
			 this.adventures = response;
		  },
		);
		}
	}
	
	sortByTitle(): void{
		if(this.flagTitle === false){
			this.flagTitle = true;
		}
		else{
			this.flagTitle = false;
		}
		this.adventureService.sortAdventuresByTitle(this.id, this.flagTitle).subscribe(
		 (response: Adventure[]) => {
			 this.adventures = response;
		  }
		);
	}
	sortByPrice(): void{
		if(this.flagPrice === false){
			this.flagPrice = true;
		}
		else{
			this.flagPrice = false;
		}
		this.adventureService.sortAdventuresByPrice(this.id, this.flagPrice).subscribe(
		 (response: Adventure[]) => {
			 this.adventures = response;
		  }
		);
	}
	sortByCapacity(): void{
		if(this.flagCapacity === false){
			this.flagCapacity = true;
		}
		else{
			this.flagCapacity = false;
		}
		
		this.adventureService.sortAdventuresByCapacity(this.id, this.flagCapacity).subscribe(
		 (response: Adventure[]) => {
			 this.adventures = response;
		  }
		);
	}
}
