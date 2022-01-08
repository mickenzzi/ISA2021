package application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import application.model.Adventure;
import application.model.User;
import application.repository.AdventureRepository;
import application.repository.UserRepository;
import application.service.AdventureService;

@Service
public class AdventureServiceImpl implements AdventureService{
	@Autowired
	private AdventureRepository adventureRepository;
	@Autowired
	private UserRepository userRepository; 
	
	@Override
	public Adventure findById(Long id)  throws AccessDeniedException{
		return adventureRepository.findById(id).orElseGet(null);
	}

	@Override
	public List<Adventure> findAll()  throws AccessDeniedException{
		return adventureRepository.findAll();
	}

	@Override
	public Adventure save(Adventure adventure) {
		return adventureRepository.save(adventure);
	}

	@Override
	public Adventure createAdventure(Adventure adventure,Long id) {
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
		adventure1.setInstructorBiography(adventure.getCancelCondition());
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

}
