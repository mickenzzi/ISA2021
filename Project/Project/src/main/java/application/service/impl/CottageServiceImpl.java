package application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import application.model.Cottage;
import application.repository.CottageRepository;
import application.service.CottageService;

@Service
public class CottageServiceImpl implements CottageService {
	
	@Autowired
	private CottageRepository cottageRepository;
	
	@Override
	public Cottage findById(Long id) throws AccessDeniedException {
		return cottageRepository.findById(id).orElseGet(null);
	}

	@Override
	public List<Cottage> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cottage save(Cottage cottage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cottage createCottage(Cottage cottage, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCottage(Cottage cottage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Cottage> sortByTitle(Long instructorId, boolean asc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cottage> sortByPrice(Long instructorId, boolean asc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cottage> sortByCapacity(Long instructorId, boolean asc) {
		// TODO Auto-generated method stub
		return null;
	}
}
