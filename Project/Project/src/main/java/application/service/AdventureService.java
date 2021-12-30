package application.service;

import java.util.List;

import application.model.Adventure;

public interface AdventureService {
	Adventure findById(Long id);
	List<Adventure> findAll();
	Adventure save(Adventure adventure);
	Adventure createAdventure(Adventure adventure);
	void updateAdventure(Adventure adventure);
}
