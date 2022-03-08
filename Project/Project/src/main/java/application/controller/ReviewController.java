package application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.service.ReviewService;
import application.model.Review;
import application.model.Reservation;

@RestController
@RequestMapping(value = "/api/reviews")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
	@GetMapping(value = "/getAllReviews", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
	public ResponseEntity<List<Review>> getAllReviews() {
		List<Review> reviews = reviewService.findAll();
		System.out.println("The task /getAllReviews was successfully completed.");
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}
	

	@GetMapping(value = "/getAllUserReservations/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR') or hasRole('USER')")
	public ResponseEntity<List<Reservation>> getAllReservations(@PathVariable("userId") Long userId) {
		List<Reservation> reservations = reviewService.findAllUserReservation(userId);
		System.out.println("The task /getAllUserReservation was successfully completed.");
		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getReviewById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR') or hasRole('USER')")
	public ResponseEntity<Review> getReviewById(@PathVariable("id") Long id) {
		Review review = reviewService.findById(id);
		if (review == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(review, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/getReview/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Review>> getReview(@PathVariable("id") Long id) {
		Review review = reviewService.findById(id);
		List<Review> reviews = new ArrayList<Review>();
		reviews.add(review);
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}
	
	@PostMapping(value = "/createReview/{adventureId}", produces = MediaType.APPLICATION_JSON_VALUE )
	@PreAuthorize("hasRole('USER') or hasRole('INSTRUCTOR')")
	public ResponseEntity<Review> createReview(@RequestBody Review review1,@PathVariable("adventureId") Long adventureId) {
		Review review = reviewService.create(review1,adventureId);
		System.out.println("The task /createReview was successfully completed.");
		return new ResponseEntity<>(review, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/deleteReview/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('INSTRUCTOR')")
	public ResponseEntity<Review> deleteReview(@PathVariable("id") Long id) {
		if (id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		reviewService.delete(id);
		System.out.println("The task /deleteReview was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/enableReview/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
	public ResponseEntity<Review> enableReview(@PathVariable("id") Long id) {
		if (id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		Review review = reviewService.enableReview(id);
		System.out.println("The task /enableReview was successfully completed.");
		return new ResponseEntity<>(review,HttpStatus.OK);
	}

}
