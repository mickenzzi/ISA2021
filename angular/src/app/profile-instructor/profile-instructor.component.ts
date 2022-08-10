import {Component, OnInit} from '@angular/core'
import {UserService} from '../service/user.service';
import {User} from '../model/user';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from "rxjs";
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-profile-instructor',
  templateUrl: './profile-instructor.component.html',
  styleUrls: ['./profile-instructor.component.css']
})
export class ProfileInstructorComponent implements OnInit {
  //subscribe
  user: User = new User();
  //unsubscribe
  subs: Subscription[] = [];
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService) {
  }

  ngOnInit(): void {
    if (this.currentUser === null) {
      alert('Niste se ulogovali');
      this.logOut();
    } else {
      this.getUser();
    }
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe())
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  goBack() {
    this.router.navigate(['/homeInstructor']);
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
    }));
  }

  updateUser() {
    if (this.user.password1 === undefined) {
      alert('Unesite lozinku');
    } else {
      this.subs.push(this.userService.updateUser(this.user).subscribe(() => {
        alert('Korisnik je izmenjen. Redirekcija na logovanje zbog autentifikacije');
        this.router.navigate(['/login']);
      }, (error: HttpErrorResponse) => {
        alert("Podaci nisu validni");
      }));
    }
  }

}
