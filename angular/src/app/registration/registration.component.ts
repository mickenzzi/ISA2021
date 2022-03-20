import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {UserService} from '../service/user.service';
import {User} from '../model/user';
import {Router} from '@angular/router';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  //subscribe
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
  //unsubscribe
  subs: Subscription[] = [];
  //flags
  flag1 = true;

  constructor(private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe())
  }

  goBack() {
    this.router.navigate(['/']);
  }

  createUser() {
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
    this.subs.push(this.userService.createUser(data)
      .subscribe(() => {
        if (data.firstName === "" || data.lastName === "" || data.address === "" || data.city === "" || data.country === "" || data.phone === "" || data.email === "" || data.username === "" || data.password1 === "" || data.password2 === "" || data.role === "") {
          this.flag1 = false;
        } else {
          this.flag1 = true;
          this.router.navigate(['/login']);
          alert('Nalog poslat na verifikaciju');
        }
      }, (error: HttpErrorResponse) => {
        alert('Neophodno je popuniti sva polja ispravnim podacima.');
      }));
  }


}
