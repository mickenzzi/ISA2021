import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse} from '@angular/common/http';
import { UserService } from '../service/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { LoginDetails } from '../model/login-details';
import { LoggedUserDetails } from '../model/logged-user-details';
import { User } from '../model/user';
import { UserTokenState } from '../model/user-token-state';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
	loginDetails: LoginDetails = {
    username: '',
    password: ''
	}	
	loggedUserDetails: LoggedUserDetails = {
	role: '',
	id: 0
	}
	accesToken: string = '';
	user: User = new User();
	ust: UserTokenState = new UserTokenState();

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService,
		private userService: UserService,
    ) {
    }

    ngOnInit() {
		localStorage.removeItem('currentUser');
		localStorage.clear();
    }
	goBack(){
		this.router.navigate(['/']);
	}
	loginUser(){
		const data = {
			username : this.loginDetails.username,
			password : this.loginDetails.password
		}
		this.authenticationService.loginUser(data)
		.subscribe(
        (response)=> {
			//@ts-ignore
			const currentUser = JSON.parse(localStorage.getItem('currentUser'));
			console.log(currentUser);
			const username = currentUser.username1;
			this.userService.findUser(username).subscribe((response: User) =>{
				this.user = response;
			
			if(this.user.role === "ROLE_ADMIN"){
				alert('Uspesno ste se ulogovali');
				this.router.navigate(['/homeAdmin']);
			}
			if(this.user.role === "ROLE_INSTRUCTOR"){
				alert('Uspesno ste se ulogovali');
				this.router.navigate(['/homeInstructor']);
			}
			if(this.user.role === "ROLE_USER"){
				alert('Uspesno ste se ulogovai');
				this.router.navigate(['/homeUser', this.user.id]);
			}
			});
			},
		 (error: HttpErrorResponse) =>{
			  alert('Neophodno je prvo izvrsiti registraciju');
		  }
		);
	}

}
