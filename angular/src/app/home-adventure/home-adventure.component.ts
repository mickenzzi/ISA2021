import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../model/user';
import {Termin} from '../model/termin';
import {Adventure} from '../model/adventure';
import {UserService} from '../service/user.service';
import {RequestService} from '../service/request.service';
import {AdventureService} from '../service/adventure.service';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-home-adventure',
  templateUrl: './home-adventure.component.html',
  styleUrls: ['./home-adventure.component.css']
})
export class HomeAdventureComponent implements OnInit {
  //flag for action
  flag1: Boolean = true;
  idAdventure!: number;
  adventure: Adventure = new Adventure();
  price!: number;
  capacity!: number;
  termin: Termin = new Termin();
  user: User = new User();
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private requestService: RequestService, private adventureService: AdventureService,) {
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

  goBack(): void {
    this.router.navigate(['/homeInstructor']);
  }

  public getUser(): void {
    const username = this.currentUser.username1;
    this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
    });
  }

  public getAdventure(): void {
    this.adventureService.getAdventure(this.idAdventure).subscribe((response: Adventure) => {
      this.adventure = response;
    });
  }

  editAdventure(): void {
    this.adventureService.updateAdventure(this.adventure).subscribe(response => {
      alert('Izmenili ste avanturu.');
      this.getAdventure();
    }, (error: HttpErrorResponse) => {
      alert(error.message);
    });
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
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

  createAction(): void {
    if (this.user.id === undefined) {
    } else {
      this.adventureService.createAction(this.user.id, this.idAdventure, this.termin, this.price, this.capacity).subscribe(response => {
        alert('Uspesno ste kreirali termin za brzu rezervaciju.');
        this.flag1 = false;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

  rejectCreateAction() {
    var editButton = <HTMLInputElement>document.getElementById('editButton');
    this.flag1 = true;
    editButton.disabled = false;
  }

}
