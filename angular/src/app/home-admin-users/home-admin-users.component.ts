import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { Request } from '../model/request';
import { UserService } from '../service/user.service';
import { RequestService } from '../service/request.service';
import { HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-home-admin-users',
  templateUrl: './home-admin-users.component.html',
  styleUrls: ['./home-admin-users.component.css']
})
export class HomeAdminUsersComponent implements OnInit {
  idUser!: number;
  user: User = new User();
   //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser')); 
  public users: User[];
  
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private userService: UserService,
  ) { this.users = []; }

  ngOnInit(): void {
	if(this.currentUser === null){
		alert('Niste se ulogovali');
		this.logOut();
	}
	else{
	this.getUser();
	}
  }
  
    public getUser(): void{
		const username = this.currentUser.username1;
		this.userService.findUser(username).subscribe(
		 (response: User) => {
			 this.user = response;
			 this.getAllUsers();
			 }
		);
	}
  
   public getAllUsers(): void{
	   if(this.user.id === undefined){
		}
		else{
		this.userService.getAllUsers(this.user.id).subscribe(
		 (response: User[]) => {
			 this.users = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
		}
	}
	
	goToAdminHome(): void{
		this.router.navigate(['/homeAdmin']);
	}
	
	logOut(){
		localStorage.removeItem('currentUser');
		localStorage.clear();
		this.router.navigate(['/login']);
	}
	
	deleteUser(idUser1?: number): void{
		if(idUser1 === undefined){
			alert('Id ne postoji');
		}
		else{
		this.idUser = idUser1;
		this.userService.deleteUser(this.idUser).subscribe(
		 (response) => {
			this.getAllUsers();
			alert('Uspesno ste izbrisali korisnika');
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
		}
	}

}
