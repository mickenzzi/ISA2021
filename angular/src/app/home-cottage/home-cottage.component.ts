import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NumericLiteral } from 'typescript';
import { Cottage } from '../model/cottage';
import { CottageImage } from '../model/cottageImage';
import { Image } from '../model/image';
import { User } from '../model/user';
import { CottageService } from '../service/cottage.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-home-cottage',
  templateUrl: './home-cottage.component.html',
  styleUrls: ['./home-cottage.component.css']
})
export class HomeCottageComponent implements OnInit {

   //subscribe
   user: User = new User();
   //unsubsribe
   subs: Subscription[] = [];
   //local
   id!: number;
   //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));
  cottage: Cottage = new Cottage();
  isDisabled = true;
  images: Image [] = [];
  cottages: CottageImage [] = [];
  image!: Image;
  url1!: String;
  url2!: String;
  url3!: String;
  url4!: String;
  url5!: String;
  show1: boolean = false;
  show2: boolean = false;
  show3: boolean = false;
  show4: boolean = false;
  show5: boolean = false;
  imageIdToDelete: number = 0;
  URL_ss = "";
  URL: String = "";
  URL_R: String = "";
  URL_path: String = "/assets/img/";
  cottageId!: number;
  url_test: String = "";
  isOwner: boolean = false;
  cottageInfo: String = "Informacije o vikendici";
  latitude!: number;
  longitude!: number;
  notLoggedOrUser?: boolean;


  constructor(private router: Router, private userService: UserService, private cottageService: CottageService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.cottageId = this.route.snapshot.params['id'];
    this.getCottage();
    console.log(this.cottageId);
    if (this.currentUser === null) {
      
    } else {
      this.getUser();
      //dodati if proveru za isDisabled, ako je zauzeta vikendica ili nije trenutni korisnik VLASNIK vikendice, isDisabled = true
    }
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe())
  }

  onSelectFile1(event: any) {
    this.url1 = this.URL_path + this.url1.substring(12);
    var temp_url = this.url1
    this.cottageService.saveImage(this.cottageId, temp_url).subscribe();
    window.location.reload();
  }

  onSelectFile2(event: any) {
    this.url2 = this.URL_path + this.url2.substring(12);
    var temp_url2 = this.url2
    this.cottageService.saveImage(this.cottageId, temp_url2).subscribe();
    window.location.reload();
  }

  onSelectFile3(event: any) {
    this.url3 = this.URL_path + this.url3.substring(12);
    var temp_url3 = this.url3
    this.cottageService.saveImage(this.cottageId, temp_url3).subscribe();
    window.location.reload();
  }

  onSelectFile4(event: any) {
    this.url4 = this.URL_path + this.url4.substring(12);
    var temp_url4 = this.url4
    this.cottageService.saveImage(this.cottageId, temp_url4).subscribe();
    window.location.reload();
  }
  
  onSelectFile5(event: any) {
    this.url5 = this.URL_path + this.url5.substring(12);
    var temp_url5 = this.url5
    this.cottageService.saveImage(this.cottageId, temp_url5).subscribe();
    window.location.reload();
  }

  getCottage() {
    this.subs.push(this.cottageService.getCottage(this.cottageId).subscribe((response: Cottage) => {
      this.cottage = response;
      this.getCottageImages();
      this.isDisabled = (this.cottage.reserved || !this.isOwner) ?? false;
      if (this.cottage.reserved) {
        var editButton = <HTMLInputElement>document.getElementById('editButton');
        editButton.disabled = true;
      }
      this.latitude = this.cottage.latitude ?? 45.246068230253336;
      this.longitude = this.cottage.longitude ?? 19.851725213006898;

    }));
  }

  deleteImg(url?: String) {
    if (url === undefined) {
      alert('Url nije validan.');
    } else {
      for(var i = 0; i <this.cottages.length; i++){
        if(url === this.cottages[i].imageUrl){
          this.imageIdToDelete = this.cottages[i].id;
        } 
      }

      this.subs.push(this.cottageService.deleteCottageImage(this.imageIdToDelete).subscribe(() => {
        this.getCottageImages();
      }));
      window.location.reload();
    }
  }

  getCottageImages() {
    this.subs.push(this.cottageService.getAllCottageImages(this.cottageId).subscribe((response: CottageImage[]) => {
      this.cottages = response;
      console.log(this.cottages);
      if(this.cottages.length > 0){
        if(this.cottages[0].imageUrl){
          this.url1 = this.cottages[0].imageUrl;
          if(this.isOwner){
            this.show1 = true;
          }
        } else {
          this.url1 = "";
          this.show1 = false;
        }
        if(this.cottages[1].imageUrl){
          this.url2 = this.cottages[1].imageUrl;
          if(this.isOwner){
            this.show2 = true;
          }
        } else {
          this.url2 = "";
          this.show2 = false;
        }
        if(this.cottages[2].imageUrl){
          this.url3 = this.cottages[2].imageUrl;
          if(this.isOwner){
            this.show3 = true;
          }
        } else {
          this.url3 = "";
          this.show3 = false;
        }
        if(this.cottages[3].imageUrl){
          this.url4 = this.cottages[3].imageUrl;
          if(this.isOwner){
            this.show4 = true;
          }
        } else {
          this.url4 = "";
          this.show4 = false;
        }
        if(this.cottages[4].imageUrl){
          this.url5 = this.cottages[4].imageUrl;
          if(this.isOwner){
            this.show5 = true;
          }
        } else {
          this.url5 = "";
          this.show5 = false;
        }
      }
      
    }));
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  back() {
    this.router.navigate(['/cottageList']);
  }

  calendar() {
    this.router.navigate(['/cottageTermins/' + this.cottageId]);
  }

  subscribe() {
    if(this.currentUser === null){
      alert("Morate biti ulogovani da biste se pretplatili!");
    } else {
      //dodavanje usera listi koja je pretplacena, join table bi trebao na backu vrv
    }
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
      if(this.user.role === "ROLE_COTTAGE_OWNER"){
        this.isOwner = true;
      }
      if(this.isOwner){
        this.cottageInfo = "Izmeni vikendicu";
      } else {
        this.cottageInfo = "Informacije o vikendici";
      }

      if(this.user.role === "ROLE_USER" || this.currentUser === null){
        this.notLoggedOrUser = true;
      } else {
        this.notLoggedOrUser = false;
      }
    }));
  }

  editCottage(): void {
    this.subs.push(this.cottageService.updateCottage(this.cottage).subscribe(() => {
      alert('Izmenili ste vikendicu!');
      this.URL_ss = this.URL.substring(12);
      this.URL_R = this.URL_path + this.URL_ss;
      this.getCottage();
    }, (error: HttpErrorResponse) => {
      alert("Unos nije validan");
    }));
  }
}
