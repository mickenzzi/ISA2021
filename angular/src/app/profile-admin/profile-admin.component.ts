import {Component, OnInit} from '@angular/core'
import {UserService} from '../service/user.service';
import {User} from '../model/user';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-profile-admin',
  templateUrl: './profile-admin.component.html',
  styleUrls: ['./profile-admin.component.css']
})
export class ProfileAdminComponent implements OnInit {
  user: User = new User();
  id!: number;
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
          alert('Uspesno ste izmenili podatke');
          this.router.navigate(['/homeAdmin'])
        }
      );
    }
  }

  goBack() {
    this.router.navigate(['/homeAdmin']);
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

}
