package application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Boat;
import application.model.BoatImage;
import application.model.User;
import application.repository.BoatImageRepository;
import application.repository.BoatRepository;
import application.service.BoatService;
import application.service.UserService;

@Service
public class BoatServiceImpl implements BoatService {
	
	@Autowired
	private BoatRepository boatRepository;
	@Autowired
	private BoatImageRepository boatImageRepository;
	@Autowired
	private UserService userService;
	
	@Override
	public List<Boat> findAll() {
		return boatRepository.findAll();
	}

	@Override
	public Boat findById(Long id) {
		return boatRepository.findById(id).orElseGet(null);
	}

	@Override
	public void update(Boat boat) {
		Boat boat1 = boatRepository.findById(boat.getId()).orElseGet(null);
		boat.setName(boat1.getName());
		boat.setType(boat1.getType());
		boat.setLenght(boat1.getLenght());
		boat.setEngineNumber(boat1.getEngineNumber());
		boat.setEnginePower(boat1.getEnginePower());
		boat.setMaxSpeed(boat1.getMaxSpeed());
		boat.setNavigationEquipment(boat1.getNavigationEquipment());
		boat.setAddress(boat1.getAddress());
		boat.setDescription(boat1.getDescription());
		boat.setImage(boat1.getImage());
		boat.setCapacity(boat1.getCapacity());
		boat.setRules(boat1.getRules());
		boat.setFishingEquipment(boat1.getFishingEquipment());
		boat.setPrice(boat1.getPrice());
		boat.setInfo(boat1.getInfo());
		boat.setCancelTerms(boat1.getCancelTerms());
		
		boatRepository.save(boat);
	}

	@Override
	public void delete(Long id) {
		Boat boat = boatRepository.findById(id).orElseGet(null);
		boatRepository.delete(boat);
	}

	@Override
	public Boat save(Boat boat1, Long id) {
		Boat boat = new Boat();
		boat.setName(boat1.getName());
		boat.setType(boat1.getType());
		boat.setLenght(boat1.getLenght());
		boat.setEngineNumber(boat1.getEngineNumber());
		boat.setEnginePower(boat1.getEnginePower());
		boat.setMaxSpeed(boat1.getMaxSpeed());
		boat.setNavigationEquipment(boat1.getNavigationEquipment());
		boat.setAddress(boat1.getAddress());
		boat.setDescription(boat1.getDescription());
		boat.setImage(boat1.getImage());
		boat.setCapacity(boat1.getCapacity());
		boat.setRules(boat1.getRules());
		boat.setFishingEquipment(boat1.getFishingEquipment());
		boat.setPrice(boat1.getPrice());
		boat.setInfo(boat1.getInfo());
		boat.setCancelTerms(boat1.getCancelTerms());
		User user = userService.findById(id);
		boat.setUserBoat(user);
		
		boatRepository.save(boat1);
		return boat1;
	}


	@Override
	public List<BoatImage> findImagesByBoatId(Long id) {
		List<BoatImage> allBoatImages = boatImageRepository.findAll();
		List<BoatImage> retImages = new ArrayList<BoatImage>();
		for(BoatImage boatImage : allBoatImages) {
			if(boatImage.getBoatId().getId() == id) {
				retImages.add(boatImage);
			}
		}
		return retImages;
	}


	@Override
	public BoatImage findImageById(Long id) {
		return boatImageRepository.findById(id).orElseGet(null);
	}

	@Override
	public void updateImage(BoatImage boatImage) {
		BoatImage image1 = boatImageRepository.findById(boatImage.getId()).orElseGet(null);
		image1.setImageUrl(boatImage.getImageUrl());
		boatImageRepository.save(image1);
	}

	@Override
	public void deleteImage(Long id) {
		List<BoatImage> allImages = getAllImages();
		for(BoatImage image : allImages) 
			if(image.getId() == id) 
				boatImageRepository.delete(image);
	}

	@Override
	public boolean saveImage(String url, Long id) {
		List<BoatImage> allImages = findImagesByBoatId(id);
		boolean exists = false;
		BoatImage saveImage = new BoatImage();
		for(BoatImage image : allImages) {
			if(image.getImageUrl().equals(url) && image.getBoatId().getId() == id) {
				exists = true;
			}
		}
		if(!exists) {
			Boat boat = findById(id);
			saveImage.setImageUrl(url);
			saveImage.setBoatId(boat);
			boatImageRepository.save(saveImage);
		}
		return !exists;
	}

	@Override
	public List<BoatImage> getAllImages() {
		return boatImageRepository.findAll();
	}
	

}
