package application.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Boat;
import application.model.BoatImage;
import application.model.EntitySubscriber;
import application.model.User;
import application.repository.BoatImageRepository;
import application.repository.BoatRepository;
import application.repository.EntitySubscriberRepository;
import application.service.BoatService;
import application.service.UserService;

@Service
public class BoatServiceImpl implements BoatService {
	
	@Autowired
	private BoatRepository cottageRepository;
	@Autowired
	private BoatImageRepository cottageImageRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private EntitySubscriberRepository subscriberRespository;
	
	@Override
	public List<Boat> findAll() {
		return cottageRepository.findAll();
	}

	@Override
	public Boat findById(Long id) {
		return cottageRepository.findById(id).orElseGet(null);
	}

	@Override
	public void update(Boat cottage) {
		Boat cottage1 = cottageRepository.findById(cottage.getId()).orElseGet(null);
		cottage1.setName(cottage.getName());
		cottage1.setAddress(cottage.getAddress());
		cottage1.setType(cottage.getType());
		cottage1.setLenght(cottage.getLenght());
		cottage1.setEngineNumber(cottage.getEngineNumber());
		cottage1.setEnginePower(cottage.getEnginePower());
		cottage1.setMaxSpeed(cottage.getMaxSpeed());
		cottage1.setNavigationEquipment(cottage.getNavigationEquipment());
		cottage1.setCapacity(cottage.getCapacity());
		cottage1.setDescription(cottage.getDescription());
		cottage1.setRules(cottage.getRules());
		cottage1.setPrice(cottage.getPrice());
		cottage1.setInfo(cottage.getInfo());
		cottage1.setImage(cottage.getImage());
		cottage1.setFishingEquipment(cottage.getFishingEquipment());
		cottage1.setCancelTerms(cottage.getCancelTerms());
		cottage1.setLatitude(cottage.getLatitude());
		cottage1.setLongitude(cottage.getLongitude());
		
		cottageRepository.save(cottage1);
	}

	@Override
	public void delete(Long id) {
		Boat cottage = cottageRepository.findById(id).orElseGet(null);
		cottageRepository.delete(cottage);
	}

	@Override
	public Boat save(Boat cottage, Long id) {
		Boat cottage1 = new Boat();
		cottage1.setName(cottage.getName());
		cottage1.setAddress(cottage.getAddress());
		cottage1.setType(cottage.getType());
		cottage1.setLenght(cottage.getLenght());
		cottage1.setEngineNumber(cottage.getEngineNumber());
		cottage1.setEnginePower(cottage.getEnginePower());
		cottage1.setMaxSpeed(cottage.getMaxSpeed());
		cottage1.setNavigationEquipment(cottage.getNavigationEquipment());
		cottage1.setCapacity(cottage.getCapacity());
		cottage1.setDescription(cottage.getDescription());
		cottage1.setRules(cottage.getRules());
		cottage1.setPrice(cottage.getPrice());
		cottage1.setInfo(cottage.getInfo());
		cottage1.setImage(cottage.getImage());
		cottage1.setFishingEquipment(cottage.getFishingEquipment());
		cottage1.setCancelTerms(cottage.getCancelTerms());
		User user = userService.findById(id);
		cottage1.setUserBoat(user);
		cottage1.setLatitude(cottage.getLatitude());
		cottage1.setLongitude(cottage.getLongitude());
		
		cottageRepository.save(cottage1);
		return cottage1;
	}


	@Override
	public List<BoatImage> findImagesByCottageId(Long id) {
		List<BoatImage> allCottageImages = cottageImageRepository.findAll();
		List<BoatImage> retImages = new ArrayList<BoatImage>();
		for(BoatImage cottageImage : allCottageImages) {
			if(cottageImage.getCottageId().getId() == id) {
				retImages.add(cottageImage);
			}
		}
		return retImages;
	}


	@Override
	public BoatImage findImageById(Long id) {
		return cottageImageRepository.findById(id).orElseGet(null);
	}

	@Override
	public void updateImage(BoatImage cottageImage) {
		BoatImage image1 = cottageImageRepository.findById(cottageImage.getId()).orElseGet(null);
		image1.setImageUrl(cottageImage.getImageUrl());
		cottageImageRepository.save(image1);
	}

	@Override
	public void deleteImage(Long id) {
		List<BoatImage> allImages = getAllImages();
		for(BoatImage image : allImages) 
			if(image.getId() == id) 
				cottageImageRepository.delete(image);
	}

	@Override
	public boolean saveImage(String url, Long id) {
		List<BoatImage> allImages = findImagesByCottageId(id);
		boolean exists = false;
		System.out.println(url);
		BoatImage saveImage = new BoatImage();
		for(BoatImage image : allImages) {
			if(image.getImageUrl().equals(url) && image.getCottageId().getId() == id) {
				exists = true;
			}
		}
		if(!exists) {
			Boat cottage = findById(id);
			saveImage.setImageUrl(url);
			saveImage.setCottageId(cottage);
			System.out.println(saveImage.getImageUrl());
			cottageImageRepository.save(saveImage);
		}
		return !exists;
	}

	@Override
	public List<BoatImage> getAllImages() {
		return cottageImageRepository.findAll();
	}

	@Override
	public List<Boat> findOwnerCottages(Long id) {
		List<Boat> cottages = findAll();
		List<Boat> ownerCottages = new ArrayList<>();
		
		for(Boat c : cottages) 
			if(c.getUserBoat().getId() == id) 
				ownerCottages.add(c);
			
		return ownerCottages;
	}

	
	
	@Override
	public void subscribe(Long cottageId, Long userId) {
		Boat cottage = new Boat();
		cottage = findById(cottageId);
		User subsciber = new User();
		subsciber = userService.findById(userId);
		EntitySubscriber es = new EntitySubscriber();
		es.setBoat(cottage);
		es.setSubscriber(subsciber);
		
		boolean save = true;
		List<EntitySubscriber> allSubs = new ArrayList<>();
		
		
		for(EntitySubscriber e : allSubs) {
			if(e.getBoat().getId().equals(cottageId) && e.getSubscriber().getId().equals(userId)) {
				save = false;
				break;
			}
		}
		
		if(save) {
			subscriberRespository.save(es);
		}
		
	}
	
	public void unsubscribe(Long cottageId, Long userId) {
		List<EntitySubscriber> esList = new ArrayList<>();
		esList = subscriberRespository.findAll();
		for(EntitySubscriber es : esList) {
			if(es.getBoat().getId().equals(cottageId) && es.getSubscriber().getId().equals(userId)) {
				subscriberRespository.delete(es);
				break;
			}
		}
	
		
	}

	@Override
	public List<EntitySubscriber> findAllSubsByCottage(Long cottageId) {
		List<EntitySubscriber> subs = new ArrayList<>();
		List<EntitySubscriber> retList = new ArrayList<>();
		subs = subscriberRespository.findAll();
		for(EntitySubscriber e : subs) {
			if(e.getBoat().getId().equals(cottageId)) {
				retList.add(e);
			}
		}
		
		return retList;
	}
	

}
