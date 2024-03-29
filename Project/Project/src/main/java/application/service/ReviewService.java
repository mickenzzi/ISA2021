package application.service;

import java.util.List;

import application.model.Reservation;
import application.model.Review;

public interface ReviewService {
	Review saveReview(Review review);
	Review findById(Long id);
	Review create(Review review,Long adventureId);
	List<Review> findAll();
	void delete(Long id);
	Reservation findReservationById(Long id);
	List<Reservation> findAllUserReservation(Long userId);
	Review enableReview(Long id);
}
