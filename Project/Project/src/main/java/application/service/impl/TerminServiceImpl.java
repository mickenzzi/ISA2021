package application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import application.model.Termin;
import application.repository.TerminRepository;
import application.service.TerminService;

@Service
public class TerminServiceImpl implements TerminService{

	@Autowired
	private TerminRepository terminRepository;
	
	@Override
	public Termin findById(Long id)  throws AccessDeniedException {
		return terminRepository.findById(id).orElseGet(null);
	}

	@Override
	public List<Termin> findAll() throws AccessDeniedException{
		return terminRepository.findAll();
	}

	@Override
	public Termin save(Termin termin) {
		return terminRepository.save(termin);
	}

	@Override
	public Termin createTermin(Termin termin) {
		Termin termin1 = new Termin();
		termin1.setAdventureTermin(termin.getAdventureTermin());
		termin1.setStart(termin.getStart());
		termin1.setEnd(termin.getEnd());
		termin1.setDuration(termin.getDuration());
		termin1.setReserved(false);
		terminRepository.save(termin1);
		return termin1;
	}

	@Override
	public void updateTermin(Termin termin) {
		Termin termin1 = terminRepository.findById(termin.getId()).orElseGet(null);
		termin1.setAdventureTermin(termin.getAdventureTermin());
		termin1.setStart(termin.getStart());
		termin1.setEnd(termin.getEnd());
		termin1.setDuration(termin.getDuration());
		termin1.setReserved(termin.isReserved());
		terminRepository.save(termin1);
	}

}
