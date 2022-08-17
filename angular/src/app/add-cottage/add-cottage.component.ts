import { HttpErrorResponse } from "@angular/common/http";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Observable } from "rxjs";
import { Cottage } from "../model/cottage";
import { User } from "../model/user";
import { AdventureService } from "../service/adventure.service";
import { CottageService } from "../service/cottage.service";
import { UserService } from "../service/user.service";

@Component({
    selector: 'app-add-cottage',
    templateUrl: './add-cottage.component.html',
    styleUrls: ['./add-cottage.component.css']
})
export class AddCottageComponent implements OnInit {
    
    URL_ss = "";
    URL: string = "";
    URL_R: string = "";
    URL_path: string = "/assets/img/";
    public cottage: Cottage = new Cottage();
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
        private cottageService: CottageService
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
        this.router.navigate(['/cottageList']);
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
        this.router.navigate(['/cottageList']);
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
            numberOfRooms: this.cottage.numberOfRooms,
            numberOfBeds: this.cottage.numberOfBeds,
            image: this.URL_R,
            rules: this.cottage.rules,
            price: this.cottage.price,
            info: this.cottage.info
            }
            this.cottageService.createCottage(data, this.user.id)
            .subscribe(
            (response )=> {
                alert('Uspesno ste dodali vikendicu!');
                this.router.navigate(['/cottageList']);
            },
               (error: HttpErrorResponse) => {
               alert("Neophodno je uneti ispravne podatke.");
               });
            } 
        }
    
}
