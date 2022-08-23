package application.service;

import java.text.ParseException;
import java.util.List;

import application.model.TerminCottage;
import application.model.ReservationCottage;

public interface TerminCottageService {
	boolean createTermin(TerminCottage termin, Long cottageId) throws ParseException;
	TerminCottage save(TerminCottage terminCottage);
	TerminCottage findById(Long id);
	List<TerminCottage> findAll();
	List<TerminCottage> findAllTerminsByCottage(Long cottageId);
	void updateTermin(TerminCottage terminCottage);
	void deleteTermin(Long id);
	
	boolean createReservation(String startDate,String endDate,Long adventureId, Long userId)  throws ParseException;
	ReservationCottage saveReservation(ReservationCottage reservation);
	ReservationCottage findReservationById(Long id);
	void deleteReservation(Long reservationId);
	void deleteReservationTermin(Long reservationId,String start,String end);
	List<ReservationCottage> findAllReservationsByCottage(Long cottageId);

}
