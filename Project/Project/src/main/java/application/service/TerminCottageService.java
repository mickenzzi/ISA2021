package application.service;

import java.text.ParseException;
import java.util.List;

import application.model.TerminCottage;
import application.model.ReservationCottage;

public interface TerminCottageService {
	boolean createTermin(TerminCottage termin, Long cottageId, int i) throws ParseException;
	TerminCottage save(TerminCottage terminCottage);
	TerminCottage findById(Long id);
	List<TerminCottage> findAll();
	List<TerminCottage> findAllTerminsByCottage(Long cottageId);
	boolean updateTermin(TerminCottage terminCottage) throws ParseException;
	void deleteTermin(Long id);
	
	boolean createReservation(String startDate,String endDate,Long adventureId, Long userId)  throws ParseException;
	ReservationCottage saveReservation(ReservationCottage reservation);
	ReservationCottage findReservationById(Long id);
	void deleteReservation(Long reservationId);
	void deleteReservationTermin(Long reservationId,String start,String end);
	List<ReservationCottage> findAllReservationsByCottage(Long cottageId);
	
	boolean reserveTermin(TerminCottage termin);
	boolean cancelReservation(TerminCottage termin);
	List<TerminCottage> finishedReservations(Long cottageId) throws ParseException;

}
