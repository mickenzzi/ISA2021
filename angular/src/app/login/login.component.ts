import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';
import {AuthenticationService} from '../service/authentication.service';
import {LoginDetails} from '../model/login-details';
import {User} from '../model/user';
import {Subscription} from "rxjs";


@Component({
  selector: 'app-login', templateUrl: './login.component.html', styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  //subscribe
  loginDetails: LoginDetails = {
    username: '', password: ''
  }
  user: User = new User();

  //unsubscribe
  subs: Subscription[] = [];

  constructor(private router: Router, private authenticationService: AuthenticationService, private userService: UserService,) {
  }

  ngOnInit() {
  }

  ngOnDestroy(){
    this.subs.forEach(sub => sub.unsubscribe())
  }

  goBack() {
    this.router.navigate(['/']);
  }

  loginUser() {
    const data = {
      username: this.loginDetails.username, password: this.loginDetails.password
    }
    this.subs.push(this.authenticationService.loginUser(data).subscribe(()=>
    {
      const currentUser = this.authenticationService.getCurrentUser()
      if(currentUser === undefined || currentUser === null){
        alert("Vas nalog nije validan.")
      }
      else{
        this.userService.findUser(currentUser.username1).subscribe( response =>
          {
             this.user = response
             if (this.user.role === "ROLE_ADMIN") {
              alert('Uspesno ste se ulogovali');
              this.router.navigate(['/homeAdmin']);
            }
            if (this.user.role === "ROLE_INSTRUCTOR") {
              alert('Uspesno ste se ulogovali');
              this.router.navigate(['/homeInstructor']);
            }
            if (this.user.role === "ROLE_USER") {
              alert('Uspesno ste se ulogovali');
              this.router.navigate(['/homeUser']);
            }
          })
      }
    }, (error: HttpErrorResponse) => {
      alert("Vas nalog nije validan.")
    }))
    }
}
