package application.service;

import java.text.ParseException;
import java.util.List;

import application.model.TerminBoat;

public interface TerminBoatService {
	boolean createTermin(TerminBoat termin, Long cottageId, int i) throws ParseException;
	TerminBoat save(TerminBoat terminCottage);
	TerminBoat findById(Long id);
	List<TerminBoat> findAll();
	List<TerminBoat> findAllTerminsByCottage(Long cottageId);
	boolean updateTermin(TerminBoat terminCottage) throws ParseException;
	void deleteTermin(Long id);
	
	boolean reserveTermin(TerminBoat termin);
	boolean cancelReservation(TerminBoat termin);
	List<TerminBoat> finishedReservations(Long cottageId) throws ParseException;
}
