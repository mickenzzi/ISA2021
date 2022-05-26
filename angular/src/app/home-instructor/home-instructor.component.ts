import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../model/user';
import {Request} from '../model/request';
import {Adventure} from '../model/adventure';
import {UserService} from '../service/user.service';
import {RequestService} from '../service/request.service';
import {AdventureService} from '../service/adventure.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-home-instructor',
  templateUrl: './home-instructor.component.html',
  styleUrls: ['./home-instructor.component.css']
})
export class HomeInstructorComponent implements OnInit {
  //subscribe
  user: User = new User();
  user1: User = new User();
  adventures: Adventure[];
  requests: Request[];
  //unsubscribe
  subs: Subscription[] = [];
  //local
  idAdventure: number = 0;
  username: string = "";
  search: string = "";
  URL_ss = "";
  URL: string = "";
  URL_R: string = ""
  URL_path: string = "/assets/img/";
  textRequest: string = "";
  averageGrade = "0"
  //flags
  flagTitle?: boolean;
  flagPrice?: boolean;
  flagCapacity?: boolean;
  //show menu panel
  flag1?: boolean;
  //show request delete dialog
  flag2?: boolean;
  //is adventure reserved
  flag3?: boolean;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private requestService: RequestService, private adventureService: AdventureService) {
    this.adventures = [];
    this.requests = [];
  }

  ngOnInit(): void {
    this.flag1 = false;
    this.flag2 = false;
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

  goToProfile() {
    this.router.navigate(['/profileInstructor']);
  }

  addAdventure() {
    this.router.navigate(['/addAdventure']);
  }

  goToAdventure(idAdventure1?: number) {
    if (idAdventure1 === undefined) {
      alert('Id nije validan');
    } else {
      this.idAdventure = idAdventure1;
      this.router.navigate(['/homeAdventure', this.idAdventure]);
    }
  }

  goToCalendar() {
    this.router.navigate(['/instructorCalendar']);
  }


  public uploadImage(event: any) {
    this.URL_ss = this.URL.substring(12);
    this.URL_R = this.URL_path + this.URL_ss;
  }

  showHidden() {
    if (this.flag1 === false) {
      this.flag1 = true;
    } else {
      this.flag1 = false;
    }
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
      this.getAllAdventures();
    }));
  }

  getAllAdventures() {
    if (this.user.id === undefined) {
    } else {
      if (this.flag2 === false) {
        this.subs.push(this.adventureService.getAllAdventures(this.user.id).subscribe((response: Adventure[]) => {
          this.adventures = response;
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }));
      } else {
        this.adventures = [];
      }
    }
  }


  requestDelete() {
    if (this.flag2 === false) {
      this.flag2 = true;
      this.getAllAdventures();
    } else {
      this.flag2 = false;
    }
    this.flag1 = false;
  }


  approveRequest() {
    if (this.user.id === undefined) {
    } else {
      if (this.textRequest === undefined || this.textRequest === null || this.textRequest.length === 0) {
        alert('Morate uneti razlog zahteva za brisanje naloga');
      } else {
        this.flag2 = false;
        this.getAllAdventures();
        this.subs.push(this.requestService.createRequest(this.user.id, this.textRequest).subscribe(() => {
          alert('Poslali ste zahtev za brisanje naloga')
          this.textRequest = "";
        }));
      }
    }
  }

  rejectRequest() {
    this.flag2 = false;
    this.getAllAdventures();
  }


  deleteAdventure(idAdventure1?: number) {
    if (idAdventure1 === undefined) {
      alert('Id nije validan');
    } else {
      this.idAdventure = idAdventure1;
      this.subs.push(this.adventureService.deleteAdventure(this.idAdventure).subscribe(() => {
        alert("Obrisali ste avanturu");
        this.getAllAdventures();
      }));
    }
  }

  searchAdventure(event: any) {
    if (this.user.id === undefined) {
    } else {
      if (this.search === null || this.search.length === 0 || this.search === undefined) {
        this.getAllAdventures();
      } else {
        this.subs.push(this.adventureService.getSearchAdventures(this.user.id, this.search).subscribe((response: Adventure[]) => {
          this.adventures = response;
        }));
      }
    }
  }

  sortByTitle() {
    if (this.user.id === undefined) {
    } else {
      if (this.flagTitle === false) {
        this.flagTitle = true;
      } else {
        this.flagTitle = false;
      }
      this.subs.push(this.adventureService.sortAdventuresByTitle(this.user.id, this.flagTitle).subscribe((response: Adventure[]) => {
        this.adventures = response;
      }));
    }
  }

  sortByPrice() {
    if (this.user.id === undefined) {
    } else {
      if (this.flagPrice === false) {
        this.flagPrice = true;
      } else {
        this.flagPrice = false;
      }
      this.subs.push(this.adventureService.sortAdventuresByPrice(this.user.id, this.flagPrice).subscribe((response: Adventure[]) => {
        this.adventures = response;
      }));
    }
  }

  sortByCapacity() {
    if (this.user.id === undefined) {
    } else {
      if (this.flagCapacity === false) {
        this.flagCapacity = true;
      } else {
        this.flagCapacity = false;
      }

      this.subs.push(this.adventureService.sortAdventuresByCapacity(this.user.id, this.flagCapacity).subscribe((response: Adventure[]) => {
        this.adventures = response;
      }));
    }
  }

  sortByGrade() {
    if (this.user.id === undefined) {
    } else {
      if (this.flagPrice === false) {
        this.flagPrice = true;
      } else {
        this.flagPrice = false;
      }
      this.subs.push(this.adventureService.sortAdventuresByGrade(this.user.id, this.flagPrice).subscribe((response: Adventure[]) => {
        this.adventures = response;
      }));
    }
  }
}
