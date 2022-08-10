import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Review} from '../model/review';
import {Complaint} from '../model/complaint';
import {Comment} from '../model/comment';
import {Reservation} from '../model/reservation';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  private reviewUrl = 'http://localhost:8081/myApp/api/reviews';
  private commentUrl = 'http://localhost:8081/myApp/api/comments';
  private complaintUrl = 'http://localhost:8081/myApp/api/complains';

  constructor(
    private http: HttpClient,
    private auth: AuthenticationService
  ) {
  }

  public getAllReviews() {
    return this.http.get<Review[]>(`${this.reviewUrl}/getAllReviews`);
  }

  public getAllUserReservations(userId: number) {
    return this.http.get<Reservation[]>(`${this.reviewUrl}/getAllUserReservations/${userId}`);
  }

  public getSingleUserReservation(reservationId: number) {
    return this.http.get<Reservation[]>(`${this.reviewUrl}/getSingleUserReservation/${reservationId}`);
  }

  public getReview(id: number) {
    return this.http.get<Review>(`${this.reviewUrl}/getReviewById/${id}`);
  }

  public getSingleReview(id: number) {
    return this.http.get<Review[]>(`${this.reviewUrl}/getReview/${id}`);
  }

  public createReview(review: Review, adventureId: number) {
    return this.http.post(`${this.reviewUrl}/createReview/${adventureId}`, review);
  }

  public enableReview(reviewId: number) {
    return this.http.get<Review>(`${this.reviewUrl}/enableReview/${reviewId}`);
  }

  public deleteReview(id: number) {
    return this.http.get<Review[]>(`${this.reviewUrl}/deleteReview/${id}`);
  }

  public getAllComments() {
    return this.http.get<Comment[]>(`${this.commentUrl}/getAllComments`);
  }

  public getComment(id: number) {
    return this.http.get<Comment>(`${this.commentUrl}/getCommentById/${id}`);
  }

  public getSingleComment(id: number) {
    return this.http.get<Comment[]>(`${this.commentUrl}/getComment/${id}`);
  }

  public createComment(comment: Comment, userId: number, instructorId: number) {
    return this.http.post(`${this.commentUrl}/createComment/${userId}/${instructorId}`, comment);
  }

  public enableComment(commentId: number) {
    return this.http.get<Comment>(`${this.commentUrl}/enableComment/${commentId}`);
  }

  public deleteComment(commentId: number) {
    return this.http.get<Comment>(`${this.commentUrl}/deleteComment/${commentId}`);
  }

  public getAllComplains() {
    return this.http.get<Complaint[]>(`${this.complaintUrl}/getAllComplains`);
  }

  public getSingleComplaint(id: number) {
    return this.http.get<Complaint[]>(`${this.complaintUrl}/getComplaint/${id}`);
  }

  public getComplaint(id: number) {
    return this.http.get<Complaint>(`${this.complaintUrl}/getComplaintById/${id}`);
  }

  public createComplaint(complaint: Complaint, userId: number, adventureId: number) {
    return this.http.post(`${this.complaintUrl}/createComplaint/${userId}/${adventureId}`, complaint);
  }

  public answerComplaint(complaint: Complaint, adminId: number, complaintId: number) {
    return this.http.post(`${this.complaintUrl}/answer/${adminId}/${complaintId}`, complaint);
  }

  public deleteComplaint(complaintId: number) {
    return this.http.get<Complaint>(`${this.complaintUrl}/deleteComplaint/${complaintId}`);
  }
}
