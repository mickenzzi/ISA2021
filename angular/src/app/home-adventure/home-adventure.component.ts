import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../model/user';
import {Termin} from '../model/termin';
import {Adventure} from '../model/adventure';
import {UserService} from '../service/user.service';
import {RequestService} from '../service/request.service';
import {AdventureService} from '../service/adventure.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-home-adventure',
  templateUrl: './home-adventure.component.html',
  styleUrls: ['./home-adventure.component.css']
})
export class HomeAdventureComponent implements OnInit {
  //subscribe
  adventure: Adventure = new Adventure();
  termin: Termin = new Termin();
  user: User = new User();
  //unsubsribe
  subs: Subscription[] = [];
  //local
  idAdventure!: number;
  price!: number;
  capacity!: number;
  //flags
  flag1: Boolean = true;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private requestService: RequestService, private adventureService: AdventureService) {
  }

  ngOnInit(): void {
    if (this.currentUser === null) {
      alert('Niste se ulogovali');
      this.logOut();
    } else {
      this.getUser();
      this.idAdventure = this.route.snapshot.params['idAdventure'];
      this.getAdventure();
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

  showActionModal() {
    var editButton = <HTMLInputElement>document.getElementById('editButton');
    if (!this.flag1) {
      this.flag1 = true;
      editButton.disabled = false;
    } else {
      this.flag1 = false;
      editButton.disabled = true;
    }
  }

  rejectCreateAction() {
    var editButton = <HTMLInputElement>document.getElementById('editButton');
    this.flag1 = true;
    editButton.disabled = false;
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
    }));
  }

  getAdventure() {
    this.subs.push(this.adventureService.getAdventure(this.idAdventure).subscribe((response: Adventure) => {
      this.adventure = response;
    }));
  }

  editAdventure(): void {
    this.subs.push(this.adventureService.updateAdventure(this.adventure).subscribe(() => {
      alert('Izmenili ste avanturu.');
      this.getAdventure();
    }, (error: HttpErrorResponse) => {
      alert(error.message);
    }));
  }

  createAction() {
    if (this.user.id === undefined) {
    } else {
      this.subs.push(this.adventureService.createAction(this.user.id, this.idAdventure, this.termin, this.price, this.capacity).subscribe(() => {
        alert('Uspesno ste kreirali termin za brzu rezervaciju.');
        this.flag1 = false;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

}
