package application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Reservation;
import application.model.Review;
import application.model.User;
import application.repository.ReservationRepository;
import application.repository.ReviewRepository;
import application.repository.UserRepository;
import application.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Review saveReview(Review review) {
		return reviewRepository.save(review);
	}

	@Override
	public Review findById(Long id) {
		return reviewRepository.findById(id).orElseGet(null);
	}

	@Override
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		Review review = reviewRepository.findById(id).orElseGet(null);
		reviewRepository.delete(review);
		
	}

	@Override
	public Review create(Review review) {
		User admin = userRepository.findByUsername("mickenzi");
		Review review1 = new Review();
		review1.setComment(review.getComment());
		review1.setAdminReview(admin);
		review1.setGrade(review.getGrade());
		review1.setInstructorReview(review.getInstructorReview());
		review1.setUserReview(review.getUserReview());
		review1.setEnabled(false);
		reviewRepository.save(review1);
		return review1;
	}

	@Override
	public List<Reservation> findAllUserReservation(Long userId) {
		List<Reservation> reservations1 = new ArrayList<Reservation>();
		reservations1 = reservationRepository.findAll();
		List<Reservation> reservations = new ArrayList<Reservation>();
		for(Reservation r: reservations1) {
			if(r.getUserReservation().getId() == userId && r.isCreatedReservation()==true ) {
				reservations.add(r);
			}
		}
		return reservations;
	}
}
