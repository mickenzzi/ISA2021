import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../model/user';
import {UserService} from '../service/user.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Adventure} from "../model/adventure";
import {AdventureService} from "../service/adventure.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-home-admin-users',
  templateUrl: './home-admin-users.component.html',
  styleUrls: ['./home-admin-users.component.css']
})
export class HomeAdminUsersComponent implements OnInit {
  //subscribe
  users: User[];
  adventures: Adventure[];
  user: User = new User();
  //unsubscribe
  subs: Subscription[] = [];
  //local
  idUser!: number;
  entityOwnerUsername?: string;
  entityOwnerRole?: string;
  entityOwnerId?: number;
  //flags
  //show users table
  flag1?: boolean;
  //show adventures table
  flag2?: boolean;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private adventureService: AdventureService) {
    this.users = [];
    this.adventures = [];
  }

  ngOnInit(): void {
    if (this.currentUser === null) {
      alert('Niste se ulogovali');
      this.logOut();
    } else {
      this.flag1 = false;
      this.flag2 = false;
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

  goToAdminHome(): void {
    this.router.navigate(['/homeAdmin']);
  }

  getAllUsers() {
    if (this.user.id === undefined) {
    } else {
      this.subs.push(this.userService.getAllUsers(this.user.id).subscribe((response: User[]) => {
        this.users = response;
        if (this.flag1 === true) {
          this.flag1 = false;
          this.flag2 = false;
        } else {
          this.flag1 = true;
          this.flag2 = false;
        }
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }


  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
    }));
    this.getEntities();
    this.getAllUsers();
  }

  deleteUser(idUser1?: number) {
    if (idUser1 === undefined) {
      alert('Id ne postoji');
    } else {
      this.idUser = idUser1;
      this.subs.push(this.userService.deleteUser(this.idUser).subscribe(() => {
        this.flag1 = false;
        this.getAllUsers();
        alert('Uspesno ste izbrisali korisnika');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }


  getEntities(userId?: number, role?: string, username?: string) {
    if (userId === undefined || role === undefined || username === undefined) {
      alert("Neispravni podaci.")
    } else {
      this.entityOwnerUsername = username;
      this.entityOwnerRole = role;
      this.entityOwnerId = userId;
      if (role === "Instruktor pecanja") {
        this.subs.push(this.adventureService.getAllAdventures(this.entityOwnerId).subscribe((response: Adventure[]) => {
          this.adventures = response;
          this.flag1 = false;
          this.flag2 = true;
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }));
      }
    }
  }

  deleteAdventure(id?: number) {
    if (id === undefined) {
      alert("Neispravni podaci.");
    } else {
      this.subs.push(this.adventureService.deleteAdventure(id).subscribe(() => {
        alert("Obrisali ste avanturu");
        this.getEntities(this.entityOwnerId, this.entityOwnerRole, this.entityOwnerUsername)
      }));
    }
  }

}
