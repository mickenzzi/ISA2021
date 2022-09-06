import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Boat } from '../model/boat';
import { User } from '../model/user';
import { BoatService } from '../service/boat.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-add-boat',
  templateUrl: './add-boat.component.html',
  styleUrls: ['./add-boat.component.css']
})
export class AddBoatComponent implements OnInit {

  URL_ss = "";
    URL: string = "";
    URL_R: string = "";
    URL_path: string = "/assets/img/";
    public cottage: Boat = new Boat();
    hide = true;
    user: User = new User();
    lat: string = "";
    long: string = "";
    selectedFiles?: FileList;
    message: string[] = [];
    previews: string[] = [];
    imageInfos?: Observable<any>;
    //@ts-ignore
    currentUser = JSON.parse(localStorage.getItem('currentUser'));

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private userService: UserService,
        private cottageService: BoatService
      ) {}

    ngOnInit(): void {
        if (this.currentUser === null) {
          alert('Niste se ulogovali');
          this.logOut();
        } else {
          this.getUser();
        }
      }
    
    goBack() {
        this.router.navigate(['/boatList']);
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
    
    goToHomePage(){
        this.router.navigate(['/boatList']);
    }

    urls = []

    selectFiles(event: any): void {
      this.message = [];
      this.selectedFiles = event.target.files;
  
      this.previews = [];
      if (this.selectedFiles && this.selectedFiles[0]) {
        const numberOfFiles = this.selectedFiles.length;
        for (let i = 0; i < numberOfFiles; i++) {
          const reader = new FileReader();
  
          reader.onload = (e: any) => {
            this.previews.push(e.target.result);
          };
  
          reader.readAsDataURL(this.selectedFiles[i]);
        }
      }
    }



    addCottage(): void {
        if (this.user.id === undefined) {
        } 
        else {
            this.URL_ss = this.URL.substring(12);
            this.URL_R = this.URL_path + this.URL_ss;
            const data = {
            name: this.cottage.address,
            address: this.cottage.address,
            description: this.cottage.description,
            image: this.URL_R,
            rules: this.cottage.rules,
            price: this.cottage.price,
            info: this.cottage.info
            }
            this.cottageService.createBoat(this.cottage, this.user.id)
            .subscribe(
            (response )=> {
                alert('Uspesno ste dodali brod!');
                this.router.navigate(['/cottageList']);
            },
               (error: HttpErrorResponse) => {
               alert("Neophodno je uneti ispravne podatke.");
               });
            } 
        }
}
