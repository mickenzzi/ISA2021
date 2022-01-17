import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review } from '../model/review';
import { Comment } from '../model/comment';
import { Reservation } from '../model/reservation';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }
  private reviewUrl = 'http://localhost:8081/myApp/api/reviews';
  private commentUrl = 'http://localhost:8081/myApp/api/comments';
   
  public getAllReviews() {
	return this.http.get<Review[]>(`${this.reviewUrl}/getAllReviews`);
  }
  
  public getAllUserReservations(userId: number) {
	return this.http.get<Reservation[]>(`${this.reviewUrl}/getAllUserReservations/${userId}`);
  }
  
  public getReview(id: number) {
	return this.http.get<Review>(`${this.reviewUrl}/getReviewById/${id}`);
  }
  
  public createReview(review: Review, adventureId: number) {
	return this.http.post(`${this.reviewUrl}/createReview/${adventureId}`, review);
  }
  
  public enableReview(reviewId: number) {
	return this.http.get<Review>(`${this.reviewUrl}/enableReview/${reviewId}`);
  }

  public deleteReview(id: number){
	return this.http.get<Review>(`${this.reviewUrl}/deleteReview/${id}`);
  }
  
  public getAllComments() {
	return this.http.get<Comment[]>(`${this.commentUrl}/getAllComments`);
  }
  public getComment(id: number) {
	return this.http.get<Comment>(`${this.commentUrl}/getCommentById/${id}`);
  }
  public createComment(comment: Comment, userId: number, instructorId: number) {
	return this.http.post(`${this.commentUrl}/createComment/${userId}/${instructorId}`, comment);
  }
  public enableComment(commentId: number) {
	return this.http.get<Comment>(`${this.commentUrl}/enableComment/${commentId}`);
  }
   public deleteComment(commentId: number){
	return this.http.get<Comment>(`${this.commentUrl}/deleteComment/${commentId}`);
  }
}
