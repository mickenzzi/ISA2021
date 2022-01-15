import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Review } from '../model/review';
import { Reservation } from '../model/reservation';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }
  private reviewUrl = 'http://localhost:8081/myApp/api/reviews';
   
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
  
  public deleteReview(id: number){
	return this.http.get<Review>(`${this.reviewUrl}/deleteReview/${id}`);
  }
}
