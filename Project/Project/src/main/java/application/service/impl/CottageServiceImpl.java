package application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Cottage;
import application.model.CottageImage;
import application.model.User;
import application.repository.CottageImageRepository;
import application.repository.CottageRepository;
import application.service.CottageService;
import application.service.UserService;

@Service
public class CottageServiceImpl implements CottageService {
	
	@Autowired
	private CottageRepository cottageRepository;
	@Autowired
	private CottageImageRepository cottageImageRepository;
	@Autowired
	private UserService userService;
	
	@Override
	public List<Cottage> findAll() {
		return cottageRepository.findAll();
	}

	@Override
	public Cottage findById(Long id) {
		return cottageRepository.findById(id).orElseGet(null);
	}

	@Override
	public void update(Cottage cottage) {
		Cottage cottage1 = cottageRepository.findById(cottage.getId()).orElseGet(null);
		cottage1.setName(cottage.getName());
		cottage1.setAddress(cottage.getAddress());
		cottage1.setDescription(cottage.getDescription());
		cottage1.setNumberOfRooms(cottage.getNumberOfBeds());
		cottage1.setNumberOfRooms(cottage.getNumberOfRooms());
		cottage1.setRules(cottage.getRules());
		cottage1.setPrice(cottage.getPrice());
		cottage1.setInfo(cottage.getInfo());
		cottage1.setUserCottage(cottage.getUserCottage());
		cottage1.setImage(cottage.getImage());
		cottage1.setReserved(cottage.isReserved());
		cottage1.setLatitude(cottage.getLatitude());
		cottage1.setLongitude(cottage.getLongitude());
		
		cottageRepository.save(cottage1);
	}

	@Override
	public void delete(Long id) {
		Cottage cottage = cottageRepository.findById(id).orElseGet(null);
		cottageRepository.delete(cottage);
	}

	@Override
	public Cottage save(Cottage cottage, Long id) {
		Cottage cottage1 = new Cottage();
		cottage1.setName(cottage.getName());
		cottage1.setAddress(cottage.getAddress());
		cottage1.setDescription(cottage.getDescription());
		cottage1.setNumberOfRooms(cottage.getNumberOfRooms());
		cottage1.setNumberOfBeds(cottage.getNumberOfBeds());
		cottage1.setRules(cottage.getRules());
		cottage1.setPrice(cottage.getPrice());
		cottage1.setInfo(cottage.getInfo());
		cottage1.setImage(cottage.getImage());
		cottage1.setReserved(false);
		User user = userService.findById(id);
		cottage1.setUserCottage(user);
		cottage1.setLatitude(cottage.getLatitude());
		cottage1.setLongitude(cottage.getLongitude());
		
		cottageRepository.save(cottage1);
		return cottage1;
	}


	@Override
	public List<CottageImage> findImagesByCottageId(Long id) {
		List<CottageImage> allCottageImages = cottageImageRepository.findAll();
		List<CottageImage> retImages = new ArrayList<CottageImage>();
		for(CottageImage cottageImage : allCottageImages) {
			if(cottageImage.getCottageId().getId() == id) {
				retImages.add(cottageImage);
			}
		}
		return retImages;
	}


	@Override
	public CottageImage findImageById(Long id) {
		return cottageImageRepository.findById(id).orElseGet(null);
	}

	@Override
	public void updateImage(CottageImage cottageImage) {
		CottageImage image1 = cottageImageRepository.findById(cottageImage.getId()).orElseGet(null);
		image1.setImageUrl(cottageImage.getImageUrl());
		cottageImageRepository.save(image1);
	}

	@Override
	public void deleteImage(Long id) {
		List<CottageImage> allImages = getAllImages();
		for(CottageImage image : allImages) 
			if(image.getId() == id) 
				cottageImageRepository.delete(image);
	}

	@Override
	public boolean saveImage(String url, Long id) {
		List<CottageImage> allImages = findImagesByCottageId(id);
		boolean exists = false;
		System.out.println(url);
		CottageImage saveImage = new CottageImage();
		for(CottageImage image : allImages) {
			if(image.getImageUrl().equals(url) && image.getCottageId().getId() == id) {
				exists = true;
			}
		}
		if(!exists) {
			Cottage cottage = findById(id);
			saveImage.setImageUrl(url);
			saveImage.setCottageId(cottage);
			System.out.println(saveImage.getImageUrl());
			cottageImageRepository.save(saveImage);
		}
		return !exists;
	}

	@Override
	public List<CottageImage> getAllImages() {
		return cottageImageRepository.findAll();
	}

	@Override
	public List<Cottage> findOwnerCottages(Long id) {
		List<Cottage> cottages = findAll();
		List<Cottage> ownerCottages = new ArrayList<>();
		
		for(Cottage c : cottages) 
			if(c.getUserCottage().getId() == id) 
				ownerCottages.add(c);
			
		return ownerCottages;
	}
	

}
