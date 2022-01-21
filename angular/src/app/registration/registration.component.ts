import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse} from '@angular/common/http';
import { UserService } from '../service/user.service';
import { User } from '../model/user';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
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
  constructor(
			private userService: UserService,
			private router: Router
  ) { }

  ngOnInit(): void {
  }
  goBack(){
	this.router.navigate(['/']);
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
			this.router.navigate(['/login']);
            alert('Nalog poslat na verifikaciju');
          }
        },
        (error: HttpErrorResponse) => {
          alert('Neophodno je popuniti sva polja ispravnim podacima.');
        }

      );

  };
  

}
