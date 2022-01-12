import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse} from '@angular/common/http';
import { UserService } from '../service/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { LoginDetails } from '../model/login-details';
import { LoggedUserDetails } from '../model/logged-user-details';

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

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) {
    }

    ngOnInit() {
    }
	goBack(){
		this.router.navigate(['/']);
	}
	loginUser(): void {
		const data = {
			username : this.loginDetails.username,
			password : this.loginDetails.password
		}
		this.authenticationService.loginUser(data)
		.subscribe(
        (response: LoggedUserDetails)=> {
			this.loggedUserDetails = response;
			alert('Korisnik je prijavljen.');
			if(this.loggedUserDetails.role === "ADMIN"){
				this.router.navigate(['/homeAdmin', this.loggedUserDetails.id]);
			}
			if(this.loggedUserDetails.role === "INSTRUCTOR"){
				this.router.navigate(['/homeInstructor', this.loggedUserDetails.id]);
			}
			if(this.loggedUserDetails.role === "USER"){
				this.router.navigate(['/homeUser', this.loggedUserDetails.id]);
			}
			
			
        }

      );
	};

}
