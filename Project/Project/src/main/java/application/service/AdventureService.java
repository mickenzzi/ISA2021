package application.service;

import java.util.List;

import application.model.Adventure;
import application.model.Image;

public interface AdventureService {
	Adventure findById(Long id);
	Image findImageById(Long id);
	List<Adventure> findAll();
	Adventure save(Adventure adventure);
	Adventure createAdventure(Adventure adventure,Long id);
	void updateAdventure(Adventure adventure);
	void updateImage(Image image);
	void delete(Long id);
	List<Adventure> sortByTitle(Long instructorId,boolean asc);
	List<Adventure> sortByPrice(Long instructorId,boolean asc);
	List<Adventure> sortByCapacity(Long instructorId,boolean asc);
	List<Adventure> sortByGrade(Long instructorId,boolean asc);
	List<Image> getAdventureImages(Long adventureId);
}
