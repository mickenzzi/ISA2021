import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {User} from '../model/user';
import {Review} from '../model/review';
import {Comment} from '../model/comment';
import {Complaint} from '../model/complaint';
import {Request} from '../model/request';
import {UserService} from '../service/user.service';
import {ReviewService} from '../service/review.service';
import {RequestService} from '../service/request.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Subscription} from "rxjs"
import {ChartType} from "chart.js";
import {Loyalty} from "../model/loyalty";
import { TouchSequence } from 'selenium-webdriver';

@Component({
  selector: 'app-home-admin', templateUrl: './home-admin.component.html', styleUrls: ['./home-admin.component.css']
})
export class HomeAdminComponent implements OnInit {
  //subscribe
  user: User = new User();
  user1: User = new User();
  requests: Request[];
  reviews: Review[];
  comments: Comment[];
  complains: Complaint[];
  complaint1: Complaint = new Complaint();
  gold: Loyalty = new Loyalty();
  silver: Loyalty = new Loyalty();
  bronze: Loyalty = new Loyalty();
  //unsubscribe
  subs: Subscription[] = [];
  //local
  username: string = "";
  rejectText?: string;
  title: string = "";
  idRequest: number = 0;
  reviewId!: number;
  commentId!: number;
  complaintId!: number;
  yearProfit?: string;
  year: string = "2022";
  percent: number = 0;
  //flags
  //show menu panel
  flag1?: boolean;
  //show notifications panel
  flag2?: boolean;
  //show reject panel
  flag3?: boolean;
  //show reviews panel
  flag4?: boolean;
  //show comments panel
  flag5?: boolean;
  //show complains panel
  flag6?: boolean;
  //show complaint answer panel
  flag7?: boolean;
  //main admin
  flag8?: boolean;
  //finances
  flag9?: boolean;
  //loyalty
  flag10?: boolean;
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'))
  //chart
  barChartLabels: string[] = ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"];
  barChartData: any[] = [];
  chartData: any[] = [];
  barChartType: ChartType = "bar";


  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private requestService: RequestService, private reviewService: ReviewService) {
    this.requests = [];
    this.reviews = [];
    this.comments = [];
    this.complains = [];
  }

  ngOnInit(): void {
    this.flag1 = false;
    this.flag2 = false;
    this.flag3 = false;
    this.flag4 = false;
    this.flag5 = false;
    this.flag6 = false;
    this.flag7 = false;
    this.flag8 = true;
    this.flag9 = false;
    this.flag10 = false;
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


  closeRejectPanel() {
    this.flag3 = false;
    this.flag7 = false;
    this.getAllReviews();
    this.getAllComplains();
    this.getAllComments();
    this.getAllRequest();
    this.complaint1 = new Complaint();
    this.rejectText = ""
  }

  closeNotification() {
    this.flag2 = false;
    this.flag3 = false;
    this.flag4 = false;
    this.flag5 = false;
    this.flag6 = false;
    this.flag7 = false;
  }

  showHidden() {
    if (this.flag1 === false) {
      this.flag1 = true;
    } else {
      this.flag1 = false;
    }
  }

  showComments() {
    this.getAllComments();
    this.flag1 = false;
    this.flag2 = false;
    this.flag3 = false;
    this.flag4 = false;
    this.flag5 = true;
    this.flag6 = false;
    this.flag7 = false;
  
  }

  showNotifications() {
    this.getAllRequest();
    this.flag1 = false;
    this.flag2 = true;
    this.flag3 = false;
    this.flag4 = false;
    this.flag5 = false;
    this.flag6 = false;
    this.flag7 = false;
    this.flag9 = false;
  }

  showReviews() {
    this.getAllReviews();
    this.flag1 = false;
    this.flag2 = false;
    this.flag3 = false
    this.flag4 = true;
    this.flag5 = false;
    this.flag6 = false;
    this.flag7 = false;
    this.flag9 = false;
  }

  showComplains() {
    this.getAllComplains();
    this.flag1 = false;
    this.flag2 = false;
    this.flag3 = false;
    this.flag4 = false;
    this.flag5 = false;
    this.flag6 = true;
    this.flag7 = false;
    this.flag9 = false;
  }


  showFinances() {
    this.flag10 = false;
    if (this.flag9 === false) {
      this.flag1 = false;
      this.flag2 = false;
      this.flag3 = false
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
      this.flag7 = false;
      this.flag9 = true;
      this.getPercent();
      this.getYearProfit(this.year);
      this.getYearPerMonthProfit();
    } else {
      this.flag9 = false;
    }
  }

  showLoyaltyProgram() {
    this.flag9 = false;
    if (this.flag10 === false) {
      this.flag10 = true;
      this.getGold();
      this.getSilver();
      this.getBronze();
    } else {
      this.flag10 = false;
    }
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  redirectAdminRegistration() {
    this.router.navigate(['/registrationAdmin']);
  }

  goToProfile() {
    this.router.navigate(['/profileAdmin']);
  }

  goToHomeAdminUsers() {
    this.router.navigate(['/homeAdminUsers']);
  }

  getUser() {
    const username = this.currentUser.username1;
    this.subs.push(this.userService.findUser(username)
      .subscribe((response: User) => {
        this.user = response;
        this.flag8 = this.user.username === "mickenzi";
        if (this.user.firstTimeLogged === true) {
          alert('Korisnik se loguje prvi put,neophodno je da potvrdi lozinku');
          this.router.navigate(['/profileAdmin']);
        }
      }));
  }

  getAllRequest() {
    if (this.user.id === undefined) { 
    } else {
      this.subs.push(this.requestService.getAllRequest(this.user.id).subscribe((response) => {
        this.requests = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  getRequest() {
    if (this.idRequest === undefined) {
    } else {
      this.subs.push(this.requestService.getRequest(this.idRequest).subscribe((response) => {
        this.requests = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  getAllComments() {
    this.subs.push(this.reviewService.getAllComments().subscribe((response) => {
      this.comments = response;
    }, (error: HttpErrorResponse) => {
      alert(error.message);
    }));
  }

  getComment() {
    if (this.commentId === undefined) {
    } else {
      this.reviewService.getSingleComment(this.commentId).subscribe((response) => {
        this.comments = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

  getAllComplains() {
    this.subs.push(this.reviewService.getAllComplains().subscribe((response) => {
      this.complains = response;
    }, (error: HttpErrorResponse) => {
      alert(error.message);
    }));
  }

  getComplaint(id?: number) {
    if (id === undefined) {
    } else {
      this.subs.push(this.reviewService.getSingleComplaint(id).subscribe((response) => {
        this.complains = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  getAllReviews() {
    this.subs.push(this.reviewService.getAllReviews().subscribe((response) => {
      this.reviews = response;
    }, (error: HttpErrorResponse) => {
      alert(error.message);
    }));
  }

  getReview() {
    if (this.reviewId === undefined) {
    } else {
      this.subs.push(this.reviewService.getSingleReview(this.reviewId).subscribe((response) => {
        this.reviews = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  getYearProfit(event: any) {
    this.subs.push(this.userService.getYearProfit(this.year).subscribe((response) => {
      this.yearProfit = response;
      this.yearProfit = this.yearProfit + " E"
    }));
  }

  getYearPerMonthProfit() {
    this.subs.push(this.userService.getYearPerMonthProfit("2022").subscribe((response) => {
      this.chartData = response;
      this.barChartData = [{data: this.chartData, label: 'Prihod po mesecima za 2022.godinu prikazan u eurima'}]
    }));
  }

  getPercent() {
    this.subs.push(this.userService.getPercent().subscribe((response) => {
      this.percent = response;
    }));
  }

  getGold() {
    this.subs.push(this.userService.getGold().subscribe((response) => {
      this.gold = response;
    }));
  }

  getSilver() {
    this.subs.push(this.userService.getSilver().subscribe((response) => {
      this.silver = response;
    }));
  }

  getBronze() {
    this.subs.push(this.userService.getBronze().subscribe((response) => {
      this.bronze = response;
    }));
  }

  editGold(goldPoints?: number) {
    if (goldPoints === undefined) {
    } else {
      this.subs.push(this.userService.updateLoyalty("GOLD", goldPoints).subscribe(() => {
        this.getGold();
        alert('Uspesno ste izmenili granicu za zlatne korisnike')
      }));
    }
  }

  editSilver(silverPoints?: number) {
    if (silverPoints === undefined) {
    } else {
      this.subs.push(this.userService.updateLoyalty("SILVER", silverPoints).subscribe(() => {
        this.getSilver();
        alert('Uspesno ste izmenili granicu za srebrne korisnike')
      }));
    }
  }

  editBronze(bronzePoints?: number) {
    if (bronzePoints === undefined) {
    } else {
      this.subs.push(this.userService.updateLoyalty("BRONZE", bronzePoints).subscribe(() => {
        this.getBronze();
        alert('Uspesno ste izmenili granicu za bronzane korisnike')
      }));
    }
  }

  enableUser(username1?: string, title1?: string, idRequest1?: number) {
    if (username1 === undefined) {
      alert("Korisnicko ime ne postoji");
    } else {
      this.username = username1;
      if (title1 === undefined) {
        alert("Naslov nije validan.");
      } else {
        this.title = title1;
        if (idRequest1 === undefined) {
          alert("Id nije validan.");
        } else {
          this.idRequest = idRequest1;
          if (this.title.includes("brisanje")) {
            this.flag3 = true;
            if (this.rejectText === undefined || this.rejectText === null || this.rejectText.length === 0) {
              alert('Unos razloga odbijanja je obavezan');
              this.getRequest();
            } else {
              this.subs.push(this.userService.approveDeleteRequest(this.username, this.idRequest, this.rejectText).subscribe((response) => {
                alert("Nalog je obrisan");
                this.user1 = response;
                this.getAllRequest();
                this.flag3 = false;
                this.rejectText = undefined;
              }));
            }
          } else {
            this.subs.push(this.userService.enableUser(this.username, this.idRequest).subscribe((response) => {
              alert("Nalog je verifikovan");
              this.user1 = response;
              this.getAllRequest();
              this.flag3 = false;
              this.rejectText = undefined;
            }));
          }
        }
      }
    }
  }

  disableUser(username1?: string, title1?: string, idRequest1?: number) {
    if (username1 === undefined) {
      alert("Korisnicko ime ne postoji");
    } else {
      this.username = username1;
      if (title1 === undefined) {
        alert("Naslov nije validan.");
      } else {
        this.title = title1;
        if (idRequest1 === undefined) {
          alert("Id nije validan.");
        } else {
          this.idRequest = idRequest1;
          if (this.title.includes("brisanje")) {
            this.flag3 = true;
            if (this.rejectText === undefined || this.rejectText === null || this.rejectText.length === 0) {
              alert('Unos razloga odbijanja je obavezan');
              this.getRequest();
            } else {
              this.subs.push(this.userService.rejectDeleteRequest(this.username, this.idRequest, this.rejectText).subscribe((response) => {
                alert("Nije odobreno brisanje naloga");
                this.flag3 = false;
                this.user1 = response;
                this.getAllRequest();
                this.rejectText = undefined;
              }));
            }
          } else {
            this.flag3 = true;
            if (this.rejectText === undefined || this.rejectText === null || this.rejectText.length === 0) {
              alert('Unos razloga odbijanja je obavezan');
              this.getRequest();
            } else {
              this.subs.push(this.userService.disableUser(this.username, this.idRequest, this.rejectText).subscribe((response) => {
                alert("Nalog nije verifikovan.");
                this.flag3 = false;
                this.user1 = response;
                this.getAllRequest();
                this.rejectText = undefined;
              }));
            }
          }
        }
      }
    }
  }

  enableReview(reviewId1?: number) {
    if (reviewId1 === undefined) {
      alert('Neispravan id');
    } else {
      this.reviewId = reviewId1;
      this.subs.push(this.reviewService.enableReview(this.reviewId).subscribe(() => {
        this.getAllReviews();
        alert('Validirali ste recenziju');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  disableReview(reviewId1?: number) {
    if (reviewId1 === undefined) {
      alert('Neispravan id');
    } else {
      this.reviewId = reviewId1;
      this.subs.push(this.reviewService.deleteReview(this.reviewId).subscribe(() => {
        this.getAllReviews();
        alert('Komentar nije prosao validaciju');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  deleteReview(reviewId1?: number) {
    if (reviewId1 === undefined) {
      alert('Neispravan id');
    } else {
      this.reviewId = reviewId1;
      this.subs.push(this.reviewService.deleteReview(this.reviewId).subscribe(() => {
        this.getAllReviews();
        alert('Obrisali ste komentar');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }


  enableComment(commentId1?: number) {
    if (commentId1 === undefined) {
      alert('Neispravan id');
    } else {
      this.commentId = commentId1;
      this.subs.push(this.reviewService.enableComment(this.commentId).subscribe(() => {
        this.getAllComments();
        alert('Validirali ste komentar');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  deleteComment(commentId1?: number) {
    if (commentId1 === undefined) {
    } else {
      this.commentId = commentId1;
      this.subs.push(this.reviewService.deleteComment(this.commentId).subscribe(() => {
        this.getAllComments();
        alert('Obrisali ste komentar');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  deleteComplaint(complaintId1?: number) {
    if (complaintId1 === undefined) {
      alert('Neispravan id');
    } else {
      this.complaintId = complaintId1;
      this.subs.push(this.reviewService.deleteComplaint(this.complaintId).subscribe(() => {
        this.getAllComplains();
        alert('Obrisali ste žalbu');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }));
    }
  }

  answer(complaintId1?: number) {
    if (this.user.id === undefined) {
    } else {
      this.flag7 = true;
      if (complaintId1 === undefined || this.complaint1.content === undefined || this.complaint1.content === null || this.complaint1.content.length === 0) {
        alert('Unesite odgovor.');
        this.getComplaint(complaintId1);
      } else {
        this.complaintId = complaintId1;
        this.subs.push(this.reviewService.answerComplaint(this.complaint1, this.user.id, this.complaintId).subscribe(() => {
          this.getAllComplains();
          this.flag7 = false;
          alert('Uspesno ste odgovorili na zalbu');
          this.complaint1.content = undefined;
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }));
      }
    }
  }


  editPercent() {
    this.subs.push(this.userService.editPercent(this.percent.toString()).subscribe(() => {
      alert('Uspesno ste izmenili procenat.');
    }));
  }

}
