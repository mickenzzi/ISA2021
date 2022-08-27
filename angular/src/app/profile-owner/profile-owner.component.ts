import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Subscription } from "rxjs";
import { User } from "../model/user";
import { UserService } from "../service/user.service";
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { RequestService } from "../service/request.service";

@Component({
    selector: 'app-profile-owner',
    templateUrl: './profile-owner.component.html',
    styleUrls: ['./profile-owner.component.css']
})
export class ProfileOwnerComponent implements OnInit {

    //subscribe
    user: User = new User();
    //unsubsribe
    subs: Subscription[] = [];
    //@ts-ignore
    currentUser = JSON.parse(localStorage.getItem('currentUser'));

    toogleRequest: boolean = false;
    requestText: string = "";

    constructor(private router: Router, private userService: UserService, private requestService: RequestService) {
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
      
      openRequest() {
        if(this.toogleRequest){
          this.toogleRequest = false;
        }
        else if (!this.toogleRequest){
          this.toogleRequest = true;
        }
      }
    
      sendRequest() {
        if (this.user.id === undefined) {
        } else {
          if (this.requestText === undefined || this.requestText === null || this.requestText.length === 0) {
            alert('Morate uneti razlog za brisanje naloga!');
          } else {
            this.subs.push(this.requestService.createRequest(this.user.id, this.requestText).subscribe(() => {
              alert('Poslali ste zahtev za brisanje naloga.')
              this.requestText = "";
              this.toogleRequest = false;
            }));
          }
        }
      }
      
      cancelRequest() {
        this.toogleRequest = false;
      }

      getUser() {
        const username = this.currentUser.username1;
        this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
          this.user = response;
        }));
      }
    
      updateUser() {
        console.log(this.user);
        if (this.user.password1 === undefined) {
          alert('Unesite lozinku');
        } else {
          this.subs.push(this.userService.updateUser(this.user).subscribe(() => {
            alert('Korisnik je izmenjen. Redirekcija na logovanje zbog autentifikacije');
            this.logOut()
          }, (error: HttpErrorResponse) => {
            alert("Podaci nisu validni");
          }));
        }
      }

}