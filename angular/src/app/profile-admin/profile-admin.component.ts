import {Component, OnInit} from '@angular/core'
import {UserService} from '../service/user.service';
import {User} from '../model/user';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-profile-admin',
  templateUrl: './profile-admin.component.html',
  styleUrls: ['./profile-admin.component.css']
})
export class ProfileAdminComponent implements OnInit {
  //subscribe
  user: User = new User();
  //unsubsribe
  subs: Subscription[] = [];
  //local
  id!: number;
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
    this.router.navigate(['/homeAdmin']);
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
        alert('Uspesno ste izmenili podatke');
        this.router.navigate(['/homeAdmin'])
      }));
    }
  }
}
