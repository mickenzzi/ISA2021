import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review } from '../model/review';
import { Complaint } from '../model/complaint';
import { Comment } from '../model/comment';
import { Reservation } from '../model/reservation';
import {AuthenticationService} from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(
  private http: HttpClient,
  private auth: AuthenticationService
  ) { }
  
  private reviewUrl = 'http://localhost:8081/myApp/api/reviews';
  private commentUrl = 'http://localhost:8081/myApp/api/comments';
  private complaintUrl = 'http://localhost:8081/myApp/api/complains';
  private token = this.auth.getToken();
  private reqHeader = new HttpHeaders().set('Authorization', 'Bearer ' + this.token);
   
  public getAllReviews() {
	return this.http.get<Review[]>(`${this.reviewUrl}/getAllReviews`, {headers: this.reqHeader});
  }
  
  public getAllUserReservations(userId: number) {
	return this.http.get<Reservation[]>(`${this.reviewUrl}/getAllUserReservations/${userId}`, {headers: this.reqHeader});
  }
  
  public getReview(id: number) {
	return this.http.get<Review>(`${this.reviewUrl}/getReviewById/${id}`, {headers: this.reqHeader});
  }
  
  public createReview(review: Review, adventureId: number) {
	return this.http.post(`${this.reviewUrl}/createReview/${adventureId}`, review, {headers: this.reqHeader});
  }
  
  public enableReview(reviewId: number) {
	return this.http.get<Review>(`${this.reviewUrl}/enableReview/${reviewId}`, {headers: this.reqHeader});
  }

  public deleteReview(id: number){
	return this.http.get<Review>(`${this.reviewUrl}/deleteReview/${id}`, {headers: this.reqHeader});
  }
  
  public getAllComments() {
	return this.http.get<Comment[]>(`${this.commentUrl}/getAllComments`, {headers: this.reqHeader});
  }
  public getComment(id: number) {
	return this.http.get<Comment>(`${this.commentUrl}/getCommentById/${id}`, {headers: this.reqHeader});
  }
  public createComment(comment: Comment, userId: number, instructorId: number) {
	return this.http.post(`${this.commentUrl}/createComment/${userId}/${instructorId}`, comment, {headers: this.reqHeader});
  }
  public enableComment(commentId: number) {
	return this.http.get<Comment>(`${this.commentUrl}/enableComment/${commentId}`, {headers: this.reqHeader});
  }
  public deleteComment(commentId: number){
	return this.http.get<Comment>(`${this.commentUrl}/deleteComment/${commentId}`, {headers: this.reqHeader});
  }
  
  public getAllComplains() {
	return this.http.get<Complaint[]>(`${this.complaintUrl}/getAllComplains`, {headers: this.reqHeader});
  }
  public getComplaint(id: number) {
	return this.http.get<Complaint>(`${this.complaintUrl}/getComplaintById/${id}`, {headers: this.reqHeader});
  }
  public createComplaint(complaint: Complaint, userId: number, adventureId: number) {
	return this.http.post(`${this.complaintUrl}/createComplaint/${userId}/${adventureId}`, complaint, {headers: this.reqHeader});
  }
  public answerComplaint(complaint: Complaint, adminId: number, complaintId: number) {
	return this.http.post(`${this.complaintUrl}/answer/${adminId}/${complaintId}`, complaint, {headers: this.reqHeader});
  }
  public deleteComplaint(complaintId: number){
	return this.http.get<Complaint>(`${this.complaintUrl}/deleteComplaint/${complaintId}`, {headers: this.reqHeader});
  }
}
