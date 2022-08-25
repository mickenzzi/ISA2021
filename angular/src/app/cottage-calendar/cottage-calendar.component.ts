import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Cottage } from '../model/cottage';
import { Termin } from '../model/termin';
import { TerminCottage } from '../model/terminCottage';
import { User } from '../model/user';
import { CottageService } from '../service/cottage.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-cottage-calendar',
  templateUrl: './cottage-calendar.component.html',
  styleUrls: ['./cottage-calendar.component.css']
})
export class CottageCalendarComponent implements OnInit {

  //subscribe
  user: User = new User();
  //unsubsribe
  subs: Subscription[] = [];
  //local
  id!: number;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));
  cottage: Cottage = new Cottage();
  cottageId!: number;
  isOwner: boolean = false;
  title: String = "Termini";
  termin: TerminCottage = new TerminCottage;
  termins: TerminCottage[] = new Array<TerminCottage>();
  
  constructor(private router: Router, private userService: UserService, private cottageService: CottageService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.cottageId = this.route.snapshot.params['id'];
    this.getCottage();
    //console.log(this.cottageId);
    this.getAllTermins();
    if (this.currentUser === null) {
      
    } else {
      this.getUser();
      //dodati if proveru za isDisabled, ako je zauzeta vikendica ili nije trenutni korisnik VLASNIK vikendice, isDisabled = true
    }
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe())
  }

  getCottage() {
    this.subs.push(this.cottageService.getCottage(this.cottageId).subscribe((response: Cottage) => {
      this.cottage = response;

    }));
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  back() {
    this.router.navigate(['/cottage/' + this.cottageId]);
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
      if(this.user.role === "ROLE_COTTAGE_OWNER"){
        this.isOwner = true;
      }
      if(this.isOwner){
        this.title = "Termini";
      } else {
        this.title = "Rezervisite termin";
      }
    }));
  }

  createTermin() {
    if (this.user.id === undefined || this.termin.start === undefined || this.termin.end === undefined || this.termin.capacity === undefined) {
      alert("Popunite sva polja!");
    } else {
      if (new Date(this.termin.start ?? "") >= new Date(this.termin.end ?? "")) {
        alert("Datumi nisu validni.");
      }
      else if (this.termin.start.length !== 20 || this.termin.end.length !== 20) {
        alert("Nevalidan format datuma.");
      }
      else {
        this.subs.push(this.cottageService.createTermin(this.termin, this.cottageId).subscribe(() => {
          this.getAllTermins();
          this.termin = new TerminCottage();
          alert('Uspesno ste kreirali termin');
        }, (error: HttpErrorResponse) => {
          alert("Termin vec postoji.")
        }));
      }
    }
  }

  getAllTermins() {
    if (this.cottageId === undefined) { }
    else {
      this.subs.push(this.cottageService.getCottageTermins(this.cottageId).subscribe((response) => {
        this.termins = response;
        console.log(this.termins);
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

}
