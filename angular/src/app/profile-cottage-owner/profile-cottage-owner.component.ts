import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { User } from "../model/user";
import { UserService } from "../service/user.service";

@Component({
    selector: 'app-profile-cottage-owner',
    templateUrl: './profile-cottage-owner.component.html',
    styleUrls: ['./profile-cottage-owner.component.css']
})
export class ProfileCottageOwnerComponent implements OnInit {

    //subscribe
    user: User = new User();
    //unsubsribe
    subs: Subscription[] = [];
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
    
      goBack() {
        this.router.navigate(['/homeCottageOwner']);
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
            this.router.navigate(['/homeCottageOwner'])
          }));
        }
      }

}