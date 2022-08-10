import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../model/user';
import { Termin } from '../model/termin';
import { Adventure } from '../model/adventure';
import { UserService } from '../service/user.service';
import { RequestService } from '../service/request.service';
import { AdventureService } from '../service/adventure.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import {Image} from '../model/image';

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
  images: Image []  = [];
  url1: string = "";
  url2: string = "";
  url3: string = "";
  //unsubsribe
  subs: Subscription[] = [];
  //local
  idAdventure!: number;
  price!: number;
  capacity!: number;
  //flags
  flag1: Boolean = true;
  isDisabled: boolean = false;
  lat = 44.38605;
  long = 19.10247;
  googleMapType = 'satellite';
  URL_ss = "";
  URL: string = "";
  URL_R: string = "";
  URL_path: string = "/assets/img/";
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

  onSelectFile1(event: any) {
    this.url1 = this.URL_path + this.url1.substring(12);
    this.images[0].url = this.url1;
    this.adventureService.updateImage(this.images[0]).subscribe();
  }
  onSelectFile2(event: any) {
    this.url2 = this.URL_path + this.url2.substring(12);
    this.images[1].url = this.url2;
    this.adventureService.updateImage(this.images[1]).subscribe();
  }
  onSelectFile3(event: any) {
    this.url3 = this.URL_path + this.url3.substring(12);
    this.images[2].url = this.url3;
    this.adventureService.updateImage(this.images[2]).subscribe();
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
      this.getAdventure()
    }));
  }

  getAdventure() {
    this.subs.push(this.adventureService.getAdventure(this.idAdventure).subscribe((response: Adventure) => {
      this.adventure = response;
      this.getAdventureImages();
      this.lat = this.adventure.latitude ?? 44;
      this.long = this.adventure.longitude ?? 19;
      this.isDisabled = this.adventure.reserved ?? false;
      if (this.adventure.reserved) {
        var editButton = <HTMLInputElement>document.getElementById('editButton');
        editButton.disabled = true;
      }
    }));
  }

  getAdventureImages() {
    this.subs.push(this.adventureService.getAllAdventureImages(this.idAdventure).subscribe((response: Image[]) => {
      this.images = response;
      this.url1 = this.images[0].url ?? "";
      this.url2 = this.images[1].url ?? "";
      this.url3 = this.images[2].url ?? "";
    }));
  }

  editAdventure(): void {
    this.subs.push(this.adventureService.updateAdventure(this.adventure).subscribe(() => {
      alert('Izmenili ste avanturu.');
      this.URL_ss = this.URL.substring(12);
      this.URL_R = this.URL_path + this.URL_ss;
      this.getAdventure();
    }, (error: HttpErrorResponse) => {
      alert("Unos nije validan");
    }));
  }

  createAction() {
    if (this.user.id === undefined) {
    } else {
      if (new Date(this.termin.start ?? "") >= new Date(this.termin.end ?? "")) {
        alert("Datumi nisu validni")
      }
      else {
        this.subs.push(this.adventureService.createAction(this.user.id, this.idAdventure, this.termin, this.price, this.capacity).subscribe(() => {
          alert('Uspesno ste kreirali termin za brzu rezervaciju.');
          this.flag1 = true;
        }, (error: HttpErrorResponse) => {
          alert("Postoji vec definisana akcija u ovom terminu");
        }));
      }
    }
  }

}
