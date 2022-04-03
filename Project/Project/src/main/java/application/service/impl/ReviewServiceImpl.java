package application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import application.model.Adventure;
import application.model.Reservation;
import application.model.Review;
import application.model.User;
import application.repository.AdventureRepository;
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

	@Autowired
	private AdventureRepository adventureRepository;

	@Autowired
	private JavaMailSender javaMailSender;

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
	public Review create(Review review, Long adventureId) {
		User admin = userRepository.findByUsername("mickenzi");
		Adventure adventure = adventureRepository.findById(adventureId).orElseGet(null);
		User instructor = userRepository.findById(adventure.getId()).orElseGet(null);
		Review review1 = new Review();
		review1.setComment(review.getComment());
		review1.setAdminReview(admin);
		review1.setGrade(review.getGrade());
		review1.setInstructorReview(instructor);
		review1.setUserReview(review.getUserReview());
		review1.setEnabled(false);
		review1.setAdventureReview(adventure);
		reviewRepository.save(review1);
		return review1;
	}

	@Override
	public List<Reservation> findAllUserReservation(Long userId) {
		List<Reservation> reservations1 = new ArrayList<Reservation>();
		reservations1 = reservationRepository.findAll();
		List<Reservation> reservations = new ArrayList<Reservation>();
		for (Reservation r : reservations1) {
			if (r.getUserReservation().getId() == userId && r.isCreatedReservation() == true) {
				reservations.add(r);
			}
		}
		return reservations;
	}

	@Override
	public Review enableReview(Long id) {
		Review review = reviewRepository.findById(id).orElseGet(null);
		User instructor = userRepository.findById(review.getInstructorReview().getId()).orElseGet(null);
		User admin = userRepository.findByUsername("mickenzi");
		User user = userRepository.findById(review.getUserReview().getId()).orElseGet(null);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(instructor.getEmail());
		mail.setFrom(admin.getEmail());
		mail.setSubject("Recenzija");
		mail.setText("Korisnik " + user.getFirstName() + " " + user.getLastName() + " je ocenio vas rad ocenom "
				+ review.getGrade() + ".");
		javaMailSender.send(mail);
		review.setEnabled(true);
		reviewRepository.save(review);
		this.setAverageGrade(review.getAdventureReview().getId());
		return review;

	}

	@Override
	public Reservation findReservationById(Long id) {
		return reservationRepository.findById(id).orElseGet(null);
	}

	private void setAverageGrade(Long adventureId) {
		System.out.println("Adventure id" + adventureId);
		Adventure adventure = adventureRepository.findById(adventureId).orElseGet(null);
		float count = 0;
		float grade = 0;
		List<Review> reviews = new ArrayList<Review>();
		reviews = reviewRepository.findAll();
		for (Review r : reviews) {
			if (r.getAdventureReview().getId() == adventureId && r.isEnabled() == true) {
				count = count + 1;
				grade = r.getGrade() + grade;
			}
		}
		if (count != 0) {
			float avg = 0;
			avg = grade / count;
			adventure.setAvgGrade(Double.toString(avg));
		}
		adventureRepository.save(adventure);
	}
}
