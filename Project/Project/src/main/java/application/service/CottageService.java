package application.service;

import java.util.List;

import application.model.Cottage;

public interface CottageService {
	Cottage findById(Long id);
	List<Cottage> findAll();
	Cottage save(Cottage cottage);
	Cottage createCottage(Cottage cottage,Long id);
	void updateCottage(Cottage cottage);
	void delete(Long id);
	List<Cottage> sortByTitle(Long instructorId,boolean asc);
	List<Cottage> sortByPrice(Long instructorId,boolean asc);
	List<Cottage> sortByCapacity(Long instructorId,boolean asc);
}
