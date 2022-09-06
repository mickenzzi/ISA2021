import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Boat } from '../model/boat';
import { User } from '../model/user';
import { BoatService } from '../service/boat.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-boat-owner-boat-list',
  templateUrl: './boat-owner-boat-list.component.html',
  styleUrls: ['./boat-owner-boat-list.component.css']
})
export class BoatOwnerBoatListComponent implements OnInit {

  //subscribe
  user: User = new User();
  //unsubsribe
  subs: Subscription[] = [];
  //local
  id!: number;
  //@ts-ignore
 currentUser = JSON.parse(localStorage.getItem('currentUser'));
 search: string = "";
 hide = true; 
 cottages: Boat[];
 constructor(private router: Router, private userService: UserService, private cottageService: BoatService) {
   this.cottages = [];
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
     this.getAllOwnerCottages();
   }));
   
 }

 getAllOwnerCottages() {
   if (this.user.id === undefined) {
   } else {
     this.subs.push(this.cottageService.getAllOwnerBoats(this.user.id).subscribe((response: Boat[]) => {
       this.cottages = response;
     }));
   }
 }

 goToHomePage() {
   this.router.navigate(['/gomeBoatOwner']);
 }

 public goToAddCottage() {
   this.router.navigate(['/boat']);
 }

 deleteCottage(id?: number) {
   if (id === undefined) {
     alert('Id nije validan.');
   } else {
     this.subs.push(this.cottageService.deleteBoat(id).subscribe(() => {
       alert("Obrisali ste brod");
       this.getAllOwnerCottages();
     }));
   }
 }

 editCottage(id?: number) {
   if (id === undefined) {
     alert('Id nije validan.');
   } else {
     this.router.navigate(['/boat/' + id]);
   }
 }

}
