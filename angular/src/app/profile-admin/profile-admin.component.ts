import { Component, OnInit } from '@angular/core'
import { HttpErrorResponse} from '@angular/common/http';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-profile-admin',
  templateUrl: './profile-admin.component.html',
  styleUrls: ['./profile-admin.component.css']
})
export class ProfileAdminComponent implements OnInit {
  user: User = new User();
  id!: number;
  constructor(
	private route: ActivatedRoute,
	private router: Router,
	private userService: UserService
	)
  {  
	}

  ngOnInit(): void{
	this.id = this.route.snapshot.params['id'];
	this.getUser();
	}
	
	public getUser(): void{
		this.userService.getUser(this.id).subscribe(
		 (response: User) => {
			 this.user = response;
		 }
		);
	}
	
	public updateUser(): void{
		if(this.user.password1 === undefined){
			alert('Unesite lozinku');
		}
		else{
		this.userService.updateUser(this.user).subscribe(
			response=>{
				alert('Korisnik je izmenjen.');
				this.router.navigate(['/homeAdmin', this.id])
			}
		);
		}
	}
	
	goBack(){
		this.router.navigate(['/homeAdmin', this.id]);
	}

}
