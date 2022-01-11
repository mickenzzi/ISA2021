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
  id!: number;
  idUser!: number;
  
  public users: User[];
  
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private userService: UserService,
  ) { this.users = []; }

  ngOnInit(): void {
	this.id = this.route.snapshot.params['id'];
	this.getAllUsers();
  }
  
  
   public getAllUsers(): void{
		this.userService.getAllUsers(this.id).subscribe(
		 (response: User[]) => {
			 this.users = response;
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
	}
	
	goToAdminHome(): void{
		this.router.navigate(['/homeAdmin',this.id]);
	}
	
	deleteUser(idUser1?: number): void{
		if(idUser1 === undefined){
			alert('Id ne postoji');
		}
		else{
		this.idUser = idUser1;
		this.userService.deleteUser(this.idUser).subscribe(
		 (response) => {
			alert('Uspesno ste izbrisali korisnika');
			this.getAllUsers();
		  },
		  (error: HttpErrorResponse) =>{
			  alert(error.message);
		  }
		);
		}
	}

}
