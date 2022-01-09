package application.service;

import java.util.List;

import application.model.Adventure;

public interface AdventureService {
	Adventure findById(Long id);
	List<Adventure> findAll();
	Adventure save(Adventure adventure);
	Adventure createAdventure(Adventure adventure,Long id);
	void updateAdventure(Adventure adventure);
	void delete(Long id);
	List<Adventure> sortByTitle(Long instructorId,boolean asc);
	List<Adventure> sortByPrice(Long instructorId,boolean asc);
	List<Adventure> sortByCapacity(Long instructorId,boolean asc);
}
