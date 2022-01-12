package application.service;

import java.text.ParseException;
import java.util.List;

import application.model.Termin;
import application.model.Reservation;

public interface TerminService {
	Termin findById(Long id);
	List<Termin> findAll();
	Termin save(Termin termin);
	Reservation saveReservation(Reservation reservation);
	Termin createTermin(Termin termin);
	boolean createReservation(String startDate,String endDate,Long adventureId, Long userId)  throws ParseException;
	void deleteReservation(Long reservationId);
	void updateTermin(Termin termin);
	List<Termin> findAllTerminsInstructor(Long instructorId);
	List<Termin> findFreeTerminsInstructor(Long instructorId);
	List<Reservation> findAllReservationsInstructor(Long instructorId);
	void delete(Long id);
}
