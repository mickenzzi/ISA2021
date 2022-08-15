import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Subscription } from 'rxjs';
import { Cottage } from '../model/cottage';
import { User } from '../model/user';
import { CottageService } from '../service/cottage.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-cottage-owner-cottage-list',
  templateUrl: './cottage-owner-cottage-list.component.html',
  styleUrls: ['./cottage-owner-cottage-list.component.css']
})
export class CottageOwnerCottageListComponent implements OnInit {

  
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
  cottages: Cottage[];
  constructor(private router: Router, private userService: UserService, private cottageService: CottageService) {
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
      this.subs.push(this.cottageService.getAllOwnerCottages(this.user.id).subscribe((response: Cottage[]) => {
        this.cottages = response;
      }));
    }
  }

  goToHomePage() {
    this.router.navigate(['/homeCottageOwner']);
  }

  public goToAddCottage() {
    this.router.navigate(['/cottage']);
  }

  deleteCottage(id?: number) {
    if (id === undefined) {
      alert('Id nije validan.');
    } else {
      this.subs.push(this.cottageService.deleteCottage(id).subscribe(() => {
        alert("Obrisali ste vikendicu");
        this.getAllOwnerCottages();
      }));
    }
  }

  editCottage(id?: number) {
    if (id === undefined) {
      alert('Id nije validan.');
    } else {
      this.router.navigate(['/cottage/' + id]);
    }
  }


}
