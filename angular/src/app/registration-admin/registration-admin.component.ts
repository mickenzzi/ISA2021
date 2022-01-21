import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse} from '@angular/common/http';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-registration-admin',
  templateUrl: './registration-admin.component.html',
  styleUrls: ['./registration-admin.component.css']
})
export class RegistrationAdminComponent implements OnInit {
 id!: number;
 user: User = {
    firstName: '',
    lastName: '',
    address: '',
    city: '',
    country: '',
    phone: '',
    email: '',
    username: '',
    password1: '',
    password2: '',
    role: ''
  }
  flag1 = true;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser')); 
  constructor(
			private userService: UserService,
			private router: Router,
			private route: ActivatedRoute,
  ) { }

  ngOnInit(): void {
		if(this.currentUser === null){
		alert('Niste se ulogovali');
		this.logOut();
		}
  }
  goBack(){
	this.router.navigate(['/homeAdmin']);
  }
  
   logOut(){
	localStorage.removeItem('currentUser');
	localStorage.clear();
	this.router.navigate(['/login']);
  }

  createUser(): void {
    const data = {
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      address: this.user.address,
      city: this.user.city,
      country: this.user.country,
      phone: this.user.phone,
      email: this.user.email,
      username: this.user.username,
      password1: this.user.password1,
      password2: this.user.password2,
      role: this.user.role
    }
    this.userService.createUser(data)
      .subscribe(
        response => {
          if (data.firstName === "" || data.lastName === "" || data.address === "" || data.city === "" || data.country === "" || data.phone === "" || data.email === "" || data.username === "" || data.password1 === "" || data.password2 === "" || data.role === "") {
            this.flag1 = false;
          }
          else {
            this.flag1 = true;
			this.router.navigate(['/homeAdmin']);
            alert('Nalog poslat na verifikaciju');
          }
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }

      );

  };
}
