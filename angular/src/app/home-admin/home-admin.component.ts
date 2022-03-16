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

@Component({
  selector: 'app-home-admin', templateUrl: './home-admin.component.html', styleUrls: ['./home-admin.component.css']
})
export class HomeAdminComponent implements OnInit {
  user: User = new User();
  user1: User = new User();
  public requests: Request[];
  public reviews: Review[];
  public comments: Comment[];
  public complains: Complaint[];
  public complaint1: Complaint = new Complaint();
  username: string = "";
  rejectText?: string;
  title: string = "";
  idRequest: number = 0;
  reviewId!: number;
  commentId!: number;
  complaintId!: number;
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
  //@ts-ignore
  currentUser = JSON.parse(localStorage.getItem('currentUser'));

  constructor(private route: ActivatedRoute, private router: Router, private userService: UserService, private requestService: RequestService, private reviewService: ReviewService,) {
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
    this.flag8 = false;
    if (this.currentUser === null) {
      alert('Niste se ulogovali');
      this.logOut();
    } else {
      this.getUser();
    }
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
    this.flag8 = false;
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
    this.flag8 = false;
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
    this.flag8 = false;
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
    this.flag8 = false;
  }

  closeNotification() {
    this.flag2 = false;
    this.flag3 = false;
    this.flag4 = false;
    this.flag5 = false;
    this.flag6 = false;
    this.flag7 = false;
    this.flag8 = false;
  }

  redirectAdminRegistration() {
    this.router.navigate(['/registrationAdmin']);
  }

  logOut() {
    localStorage.removeItem('currentUser');
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  goToProfile() {
    this.router.navigate(['/profileAdmin']);
  }

  goToHomeAdminUsers() {
    this.router.navigate(['/homeAdminUsers']);
  }

  public getUser(): void {
    const username = this.currentUser.username1;
    this.userService.findUser(username).subscribe((response: User) => {
      this.user = response;
      if (this.user.username === "mickenzi") {
        this.flag8 = true;
      } else {
        this.flag8 = false;
      }
      if (this.user.firstTimeLogged === true) {
        alert('Korisnik se loguje prvi put,neophodno je da potvrdi lozinku');
        this.router.navigate(['/profileAdmin']);
      }
    });
  }

  public getAllRequest(): void {
    if (this.user.id === undefined) {
    } else {
      this.requestService.getAllRequest(this.user.id).subscribe((response: Request[]) => {
        this.requests = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

  public getRequest(): void {
    if (this.idRequest === undefined) {
    } else {
      this.requestService.getRequest(this.idRequest).subscribe((response: Request[]) => {
        this.requests = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

  public getComment(): void {
    if (this.commentId === undefined) {
    } else {
      this.reviewService.getSingleComment(this.commentId).subscribe((response: Comment[]) => {
        this.comments = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

  public getComplaint(): void {
    if (this.complaintId === undefined) {
    } else {
      this.reviewService.getSingleComplaint(this.complaintId).subscribe((response: Complaint[]) => {
        this.complains = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

  public getReview(): void {
    if (this.reviewId === undefined) {
    } else {
      this.reviewService.getSingleReview(this.reviewId).subscribe((response: Review[]) => {
        this.reviews = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }


  public getAllReviews(): void {
    this.reviewService.getAllReviews().subscribe((response: Review[]) => {
      this.reviews = response;
    }, (error: HttpErrorResponse) => {
      alert(error.message);
    });
  }

  public getAllComments(): void {
    this.reviewService.getAllComments().subscribe((response: Comment[]) => {
      this.comments = response;
    }, (error: HttpErrorResponse) => {
      alert(error.message);
    });
  }

  public getAllComplains(): void {
    this.reviewService.getAllComplains().subscribe((response: Complaint[]) => {
      this.complains = response;
    }, (error: HttpErrorResponse) => {
      alert(error.message);
    });
  }

  public enableReview(reviewId1?: number): void {
    if (reviewId1 === undefined) {
      alert('Neispravan id');
    } else {
      this.reviewId = reviewId1;
      this.reviewService.enableReview(this.reviewId).subscribe((response) => {
        this.getAllReviews();
        alert('Validirali ste recenziju');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

  public enableComment(commentId1?: number): void {
    if (commentId1 === undefined) {
      alert('Neispravan id');
    } else {
      this.commentId = commentId1;
      this.reviewService.enableComment(this.commentId).subscribe((response) => {
        this.getAllComments();
        alert('Validirali ste komentar');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

  public deleteComment(commentId1?: number): void {
    if (commentId1 === undefined) {
    } else {
      this.commentId = commentId1;
      this.reviewService.deleteComment(this.commentId).subscribe((response) => {
        this.getAllComments();
        alert('Obrisali ste komentar');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

  public deleteComplaint(complaintId1?: number): void {
    if (complaintId1 === undefined) {
      alert('Neispravan id');
    } else {
      this.complaintId = complaintId1;
      this.reviewService.deleteComplaint(this.complaintId).subscribe((response) => {
        this.getAllComplains();
        alert('Obrisali ste zalbu');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }


  public disableReview(reviewId1?: number): void {
    if (reviewId1 === undefined) {
      alert('Neispravan id');
    } else {
      this.reviewId = reviewId1;
      this.reviewService.deleteReview(this.reviewId).subscribe((response) => {
        this.getAllReviews();
        alert('Komentar nije prosao validaciju');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }

  public deleteReview(reviewId1?: number): void {
    if (reviewId1 === undefined) {
      alert('Neispravan id');
    } else {
      this.reviewId = reviewId1;
      this.reviewService.deleteReview(this.reviewId).subscribe((response) => {
        this.getAllReviews();
        alert('Obrisali ste komentar');
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      });
    }
  }


  public enableUser(username1?: string, title1?: string, idRequest1?: number): void {
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
              this.userService.approveDeleteRequest(this.username, this.idRequest, this.rejectText).subscribe((response: User) => {
                alert("Nalog je obrisan");
                this.user1 = response;
                this.getAllRequest();
                this.flag3 = false;
                this.rejectText = undefined;
              });
            }
          } else {
            this.userService.enableUser(this.username, this.idRequest).subscribe((response: User) => {
              alert("Nalog je verifikovan");
              this.user1 = response;
              this.getAllRequest();
              this.flag3 = false;
              this.rejectText = undefined;
            });
          }
        }
      }
    }
  }

  public answer(complaintId1?: number): void {
    if (this.user.id === undefined) {
    } else {
      this.flag7 = true;
      if (complaintId1 === undefined || this.complaint1.content === undefined || this.complaint1.content === null || this.complaint1.content.length === 0) {
        alert('Unesite odgovor.');
        this.getComplaint();
      } else {
        this.complaintId = complaintId1;
        this.reviewService.answerComplaint(this.complaint1, this.user.id, this.complaintId).subscribe((response) => {
          this.getAllComplains();
          this.flag7 = false;
          alert('Uspesno ste odgovorili na zalbu');
          this.complaint1.content = undefined;
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        });
      }
    }

  }

  public disableUser(username1?: string, title1?: string, idRequest1?: number): void {
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
              this.userService.rejectDeleteRequest(this.username, this.idRequest, this.rejectText).subscribe((response: User) => {
                alert("Nije odobreno brisanje naloga");
                this.flag3 = false;
                this.user1 = response;
                this.getAllRequest();
                this.rejectText = undefined;
              });
            }
          } else {
            this.flag3 = true;
            if (this.rejectText === undefined || this.rejectText === null || this.rejectText.length === 0) {
              alert('Unos razloga odbijanja je obavezan');
              this.getRequest();
            } else {
              this.userService.disableUser(this.username, this.idRequest, this.rejectText).subscribe((response: User) => {
                alert("Nalog nije verifikovan.");
                this.flag3 = false;
                this.user1 = response;
                this.getAllRequest();
                this.rejectText = undefined;
              });
            }
          }
        }
      }
    }
  }

  public closeRejectPanel(): void {
    this.flag3 = false;
  }
}
