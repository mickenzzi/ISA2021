import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Cottage } from '../model/cottage';
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
  urls: String [] = [];
  image!: Image;
  url1!: String;
  url2: String = "";
  url3: String = "";
  url4: String = "";
  url5: String = "";
  URL_ss = "";
  URL: String = "";
  URL_R: String = "";
  URL_path: String = "/assets/img/";
  cottageId!: number;
  url_test: String = "";

  constructor(private router: Router, private userService: UserService, private cottageService: CottageService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    if (this.currentUser === null) {
      alert('Niste se ulogovali');
      this.logOut();
    } else {
      this.getUser();
      this.cottageId = this.route.snapshot.params['id'];
      this.getCottage();
      //dodati if proveru za isDisabled, ako je zauzeta vikendica ili nije trenutni korisnik VLASNIK vikendice, isDisabled = true
    }
  }

  ngOnDestroy() {
    this.subs.forEach(sub => sub.unsubscribe())
  }

  onSelectFile1(event: any) {
    /*if(event.target.files){
      var reader = new FileReader()
      reader.readAsDataURL(event.target.files[0])
      reader.onload = (event: any)=> {
        this.url1 = event.target.result
        console.log(this.url1)
        const data = { url: this.url1 }
        this.cottageService.saveImage(data, this.cottageId).subscribe();
      }
      
    } */
    console.log(this.url1)
    this.url1 = this.URL_path + this.url1.substring(12);
    console.log(this.url1)
    const data = { url: this.url1 }
    //this.cottageService.saveImage(data, this.cottageId).subscribe();
  }

  onSelectFile2(event: any) {
    this.url2 = this.URL_path + this.url2.substring(12);
    console.log(this.url2)
    var asd = this.url2
    this.cottageService.saveImage(this.cottageId, asd).subscribe();
  }

  onSelectFile3(event: any) {
    this.url3 = this.URL_path + this.url3.substring(12);
    //this.images[2].url = this.url3;
    this.cottageService.updateImage(this.images[2]).subscribe();
  }

  onSelectFile4(event: any) {
    this.url4 = this.URL_path + this.url4.substring(12);
    //this.images[3].url = this.url4;
    this.cottageService.updateImage(this.images[3]).subscribe();
  }
  
  onSelectFile5(event: any) {
    this.url5 = this.URL_path + this.url5.substring(12);
    //this.images[4].url = this.url5;
    this.cottageService.updateImage(this.images[4]).subscribe();
  }

  getCottage() {
    this.subs.push(this.cottageService.getCottage(this.cottageId).subscribe((response: Cottage) => {
      this.cottage = response;
      this.getCottageImages();
      this.isDisabled = this.cottage.reserved ?? false;
      if (this.cottage.reserved) {
        var editButton = <HTMLInputElement>document.getElementById('editButton');
        editButton.disabled = true;
      }
    }));
  }

  getCottageImages() {
    this.subs.push(this.cottageService.getAllCottageImages(this.cottageId).subscribe((response: String[]) => {
      this.urls = response;
      console.log(this.urls);
      if(this.urls.length > 0){
        this.url1 = this.urls[0] ?? "";
        this.url2 = this.urls[1] ?? "";
        this.url3 = this.urls[2] ?? "";
        this.url4 = this.urls[3] ?? "";
        this.url5 = this.urls[4] ?? "";
      }
      
    }));
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
