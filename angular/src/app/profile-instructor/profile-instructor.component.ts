import {Component, OnInit} from '@angular/core'
import {UserService} from '../service/user.service';
import {User} from '../model/user';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-profile-instructor',
  templateUrl: './profile-instructor.component.html',
  styleUrls: ['./profile-instructor.component.css']
})
export class ProfileInstructorComponent implements OnInit {

  user: User = new User();
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    if (this.currentUser === null) {
      alert('Niste se ulogovali');
      this.logOut();
    } else {
      this.getUser();
    }
  }

  public getUser(): void {
    const username = this.currentUser.username1;
    this.userService.findUser(username).subscribe(
      (response: User) => {
        this.user = response;
      }
    );
  }

  public updateUser(): void {
    if (this.user.password1 === undefined) {
      alert('Unesite lozinku');
    } else {
      this.userService.updateUser(this.user).subscribe(
        response => {
          alert('Korisnik je izmenjen.');
          this.router.navigate(['/homeInstructor'])
        }
      );
    }
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  goBack() {
    this.router.navigate(['/homeInstructor']);
  }

}
