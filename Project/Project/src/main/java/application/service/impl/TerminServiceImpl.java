package application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import application.model.Termin;
import application.model.User;
import application.model.Adventure;
import application.repository.TerminRepository;
import application.repository.AdventureRepository;
import application.repository.UserRepository;
import application.service.TerminService;

@Service
public class TerminServiceImpl implements TerminService{

	@Autowired
	private TerminRepository terminRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdventureRepository adventureRepository;
	
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

	@Override
	public List<Termin> findAllTerminsInstructor(Long instructorId) {
		User user = userRepository.findById(instructorId).orElseGet(null);
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureRepository.findAll();
		List<Termin> adventureTermin = new ArrayList<Termin>();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for(Adventure a:adventures) {
			if(a.getUserAdventure().getId() == user.getId()) {
				adventures1.add(a);
			}
		}
		List<Termin> termins = new ArrayList<Termin>();
		termins = terminRepository.findAll();
		for(Termin t:termins) {
			for(Adventure a: adventures1) {
				if(t.getAdventureTermin().getId() == a.getId()) {
					adventureTermin.add(t);
				}
			}
		}
		return adventureTermin;
	}

	@Override
	public List<Termin> findFreeTerminsInstructor(Long instructorId) {
		User user = userRepository.findById(instructorId).orElseGet(null);
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureRepository.findAll();
		List<Termin> adventureTermin = new ArrayList<Termin>();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for(Adventure a:adventures) {
			if(a.getUserAdventure().getId() == user.getId()) {
				adventures1.add(a);
			}
		}
		List<Termin> termins = new ArrayList<Termin>();
		termins = terminRepository.findAll();
		for(Termin t:termins) {
			for(Adventure a: adventures1) {
				if(t.getAdventureTermin().getId() == a.getId() && t.isReserved()==false) {
					adventureTermin.add(t);
				}
			}
		}
		return adventureTermin;
	}
	
	@Override
	public void delete(Long id) {
		Termin termin = terminRepository.findById(id).orElseGet(null);
		terminRepository.delete(termin);
	}
}
