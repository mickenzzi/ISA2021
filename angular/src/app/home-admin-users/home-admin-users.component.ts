import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../model/user';
import {UserService} from '../service/user.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Adventure} from "../model/adventure";
import {AdventureService} from "../service/adventure.service";

@Component({
  selector: 'app-home-admin-users',
  templateUrl: './home-admin-users.component.html',
  styleUrls: ['./home-admin-users.component.css']
})
export class HomeAdminUsersComponent implements OnInit {
  idUser!: number;
  entityOwnerUsername?: string;
  entityOwnerRole?: string;
  entityOwnerId?: number;
  user: User = new User();
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));
  public users: User[];
  public adventures: Adventure[];
  //show users table
  flag1?: boolean;
  //show adventures table
  flag2?: boolean;

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

  public getUser(): void {
    const username = this.currentUser.username1;
    this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
    });
  }

  public getAllUsers(): void {
    if (this.user.id === undefined) {
    } else {
      this.userService.getAllUsers(this.user.id).subscribe((response: User[]) => {
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
      });
    }
  }

  public getEntities(userId?: number, role?: string, username?: string): void {
    if (userId === undefined || role === undefined || username === undefined) {
      alert("Neispravni podaci.")
    } else {
      this.entityOwnerUsername = username;
      this.entityOwnerRole = role;
      this.entityOwnerId = userId;
      if (role === "Instruktor pecanja") {
        this.adventureService.getAllAdventures(this.entityOwnerId).subscribe((response: Adventure[]) => {
          this.adventures = response;
          this.flag1 = false;
          this.flag2 = true;
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        });
      }
    }
  }

  deleteAdventure(id?: number): void {
    if (id === undefined) {
      alert("Neispravni podaci.");
    } else {
      this.adventureService.deleteAdventure(id).subscribe((response) => {
        alert("Obrisali ste avanturu");
        this.getEntities(this.entityOwnerId, this.entityOwnerRole, this.entityOwnerUsername)
      });
    }
  }

  goToAdminHome(): void {
    this.router.navigate(['/homeAdmin']);
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  deleteUser(idUser1?: number): void {
    if (idUser1 === undefined) {
      alert('Id ne postoji');
    } else {
      this.idUser = idUser1;
      this.userService.deleteUser(this.idUser).subscribe((response) => {
        this.flag1 = false;
        this.getAllUsers();
        alert('Uspesno ste izbrisali korisnika');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

}
