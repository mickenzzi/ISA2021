package application.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import application.model.Adventure;
import application.model.User;
import application.model.Termin;
import application.repository.AdventureRepository;
import application.repository.TerminRepository;
import application.repository.UserRepository;
import application.service.AdventureService;

@Service
public class AdventureServiceImpl implements AdventureService {
	@Autowired
	private AdventureRepository adventureRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TerminRepository terminRepository;

	Comparator<Adventure> compareByTitle = new Comparator<Adventure>() {
		@Override
		public int compare(Adventure a1, Adventure a2) {
			return a1.getTitle().compareTo(a2.getTitle());
		}
	};

	Comparator<Adventure> compareByCapacity = new Comparator<Adventure>() {
		@Override
		public int compare(Adventure a1, Adventure a2) {
			return Integer.compare(a1.getMaxNumber(), a2.getMaxNumber());
		}
	};

	Comparator<Adventure> compareByPrice = new Comparator<Adventure>() {
		@Override
		public int compare(Adventure a1, Adventure a2) {
			return Double.compare(a1.getPriceList(), a2.getPriceList());
		}
	};
	
	Comparator<Adventure> compareByGrade = new Comparator<Adventure>() {
		@Override
		public int compare(Adventure a1, Adventure a2) {
			return Double.compare(Double.parseDouble(a1.getAvgGrade()), Double.parseDouble(a2.getAvgGrade()));
		}
	};

	@Override
	public Adventure findById(Long id) throws AccessDeniedException {
		return adventureRepository.findById(id).orElseGet(null);
	}

	@Override
	public List<Adventure> findAll() throws AccessDeniedException {
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures1 = adventureRepository.findAll();
		List<Termin> termins = terminRepository.findAll();
		for(Adventure a:adventures1) {
			for(Termin t:termins) {
				if(t.getAdventureTermin().getId()==a.getId() && t.isReserved() == true) {
					a.setReserved(true);
					break;
				}
				else {
					a.setReserved(false);
				}
			adventureRepository.save(a);
			}
			adventures.add(a);
		}
		return adventures;
		
	}

	@Override
	public Adventure save(Adventure adventure) {
		return adventureRepository.save(adventure);
	}

	@Override
	public Adventure createAdventure(Adventure adventure, Long id) {
		Adventure adventure1 = new Adventure();
		adventure1.setAddress(adventure.getAddress());
		adventure1.setCancelCondition(adventure.getCancelCondition());
		adventure1.setEquipment(adventure.getEquipment());
		adventure1.setImage(adventure.getImage());
		adventure1.setInstructorBiography(adventure.getCancelCondition());
		adventure1.setMaxNumber(adventure.getMaxNumber());
		adventure1.setPriceList(adventure.getPriceList());
		adventure1.setRule(adventure.getRule());
		adventure1.setTermins(adventure.getTermins());
		adventure1.setTitle(adventure.getTitle());
		adventure1.setUserAdventure(adventure.getUserAdventure());
		adventure1.setDescription(adventure.getDescription());
		User instructor = userRepository.findById(id).orElseGet(null);
		adventure1.setUserAdventure(instructor);
		adventure1.setReserved(false);
		adventureRepository.save(adventure1);
		return adventure1;
	}

	@Override
	public void updateAdventure(Adventure adventure) {
		Adventure adventure1 = adventureRepository.findById(adventure.getId()).orElseGet(null);
		adventure1.setAddress(adventure.getAddress());
		adventure1.setCancelCondition(adventure.getCancelCondition());
		adventure1.setEquipment(adventure.getEquipment());
		adventure1.setImage(adventure.getImage());
		adventure1.setInstructorBiography(adventure.getInstructorBiography());
		adventure1.setMaxNumber(adventure.getMaxNumber());
		adventure1.setPriceList(adventure.getPriceList());
		adventure1.setRule(adventure.getRule());
		adventure1.setTermins(adventure.getTermins());
		adventure1.setTitle(adventure.getTitle());
		adventure1.setDescription(adventure.getDescription());
		adventure1.setUserAdventure(adventure.getUserAdventure());
		adventureRepository.save(adventure1);
	}

	@Override
	public void delete(Long id) {
		Adventure adventure = adventureRepository.findById(id).orElseGet(null);
		adventureRepository.delete(adventure);
	}

	@Override
	public List<Adventure> sortByTitle(Long instructorId, boolean asc) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureRepository.findAll();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for (Adventure a : adventures) {
			if (a.getUserAdventure().getId() == instructorId) {
				adventures1.add(a);
			}
		}
		if (asc == true) {
			Collections.sort(adventures1, compareByTitle);
		} else {
			Collections.sort(adventures1, compareByTitle.reversed());
		}
		return adventures1;
	}

	@Override
	public List<Adventure> sortByPrice(Long instructorId, boolean asc) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureRepository.findAll();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for (Adventure a : adventures) {
			if (a.getUserAdventure().getId() == instructorId) {
				adventures1.add(a);
			}
		}
		if (asc == true) {
			Collections.sort(adventures1, compareByPrice);
		} else {
			Collections.sort(adventures1, compareByPrice.reversed());
		}
		return adventures1;
	}

	@Override
	public List<Adventure> sortByCapacity(Long instructorId, boolean asc) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureRepository.findAll();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for (Adventure a : adventures) {
			if (a.getUserAdventure().getId() == instructorId) {
				adventures1.add(a);
			}
		}
		if (asc == true) {
			Collections.sort(adventures1, compareByCapacity);
		} else {
			Collections.sort(adventures1, compareByCapacity.reversed());
		}
		return adventures1;
	}
	
	@Override
	public List<Adventure> sortByGrade(Long instructorId, boolean asc) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureRepository.findAll();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for (Adventure a : adventures) {
			if (a.getUserAdventure().getId() == instructorId) {
				adventures1.add(a);
			}
		}
		if (asc == true) {
			Collections.sort(adventures1, compareByGrade);
		} else {
			Collections.sort(adventures1, compareByGrade.reversed());
		}
		return adventures1;
	}



}
