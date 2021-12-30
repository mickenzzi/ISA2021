package application.service;

import java.util.List;

import application.model.Termin;

public interface TerminService {
	Termin findById(Long id);
	List<Termin> findAll();
	Termin save(Termin termin);
	Termin createTermin(Termin termin);
	void updateTermin(Termin termin);
}
