import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {AdventureService} from '../service/adventure.service';
import {UserService} from '../service/user.service';
import {Adventure} from '../model/adventure';
import {User} from '../model/user';
import {ActivatedRoute, Router} from '@angular/router';


@Component({
  selector: 'app-add-adventure',
  templateUrl: './add-adventure.component.html',
  styleUrls: ['./add-adventure.component.css']
})
export class AddAdventureComponent implements OnInit {
  URL_ss = "";
  URL: string = "";
  URL_R: string = "";
  URL_path: string = "/assets/img/";
  public adventure: Adventure = new Adventure();
  user: User = new User();
  lat: string = "";
  long: string = "";
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private adventureService: AdventureService,
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

  goBack() {
    this.router.navigate(['/homeInstructor']);
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  public getUser(): void {
    const username = this.currentUser.username1;
    this.userService.findUser(username).subscribe(
      (response: User) => {
        this.user = response;
      }
    );
  }

  addAdventure(): void {
    if (this.user.id === undefined) {
    } else {
      this.URL_ss = this.URL.substring(12);
      this.URL_R = this.URL_path + this.URL_ss;
      const data = {
        title: this.adventure.title,
        description: this.adventure.description,
        address: this.adventure.address,
        image: this.URL_R,
        maxNumber: this.adventure.maxNumber,
        instructorBiography: this.adventure.instructorBiography,
        rule: this.adventure.rule,
        equipment: this.adventure.equipment,
        priceList: this.adventure.priceList,
        cancelCondition: this.adventure.cancelCondition,
        latitude: this.adventure.latitude,
        longitude: this.adventure.longitude
      }
      this.adventureService.createAdventure(data, this.user.id)
        .subscribe(
          (response )=> {
            alert('Uspesno ste dodali avanturu');
            this.router.navigate(['/homeInstructor']);
          },
          (error: HttpErrorResponse) => {
            alert("Neophodno je uneti sve podatke");
          }
        );
    }
  }


}
