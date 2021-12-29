import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { Request } from '../model/request';
import { UserService } from '../service/user.service';
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
  id!: number;
  username: string = "";
  //show menu panel
  flag1?: boolean;
  //show notifications panel
  flag2?: boolean;
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private userService: UserService,
	private requestService: RequestService,
	) {
		this.requests = [];
	  }
  ngOnInit(): void {
	this.flag1 = false;
	this.flag2 = false;
	this.id = this.route.snapshot.params['id'];
	this.getUser();
	this.getAllRequest();
  }	
  showHidden(){
	if(this.flag1 === false){
		this.flag1 = true;
	}
	else{
		this.flag1 = false;
	}
  }
  
  showNotifications(){
	this.flag1=false;
	this.flag2=true;
  }
  
  closeNotification(){
	this.flag2=false;
  }
  
  redirectAdminRegistration(){
	this.router.navigate(['/registrationAdmin', this.id]);
  }
  
  logOut(){
	this.router.navigate(['/login']);
  }
  
  goToProfile(){
	this.router.navigate(['/profileAdmin']);
  }
  
  public getUser(): void{
		this.userService.getUser(this.id).subscribe(
		 (response: User) => {
			 this.user = response;
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
	
  public enableUser(username1?: string):void{
	  if(username1 === undefined){
		alert("Korisnicko ime ne postoji");
	  }
	  else{
	  this.username = username1;
	  this.userService.enableUser(this.username).subscribe(
		 (response: User) => {
			alert("Nalog je verifikovan");
			this.user1 = response;
			this.getAllRequest();
		 }
		);
	  }
  }
  
  public disableUser(username1?: string):void{
	  if(username1 === undefined){
		alert("Korisnicko ime ne postoji");
	  }
	  else{
	  this.username = username1;
	  this.userService.disableUser(this.username).subscribe(
		 (response: User) => {
			alert("Nalog nije prosao verifikaciju");
			this.user1 = response;
			this.getAllRequest();
		 }
		);
	  }
  }
}
