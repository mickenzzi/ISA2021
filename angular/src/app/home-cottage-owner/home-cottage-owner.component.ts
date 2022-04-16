import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router'; 
import { Subscription } from 'rxjs';
import { User } from '../model/user';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-home-cottage-owner',
  templateUrl: './home-cottage-owner.component.html',
  styleUrls: ['./home-cottage-owner.component.css']
})
export class HomeCottageOwnerComponent implements OnInit {
   //subscribe
   user: User = new User();
   //unsubsribe
   subs: Subscription[] = [];
   //local
   id!: number;
   //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(private router: Router, private userService: UserService) {
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

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
    }));
  }

  goToCottageList() {
    this.router.navigate(['/cottageList']);
  }

  goToProfile() {
    this.router.navigate(['/profileCottageOwner']);
  }
}
