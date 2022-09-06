package application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.Boat;
import application.model.BoatImage;
import application.model.EntitySubscriber;
import application.service.BoatService;

@RestController
@RequestMapping(value = "/api/boats")
@CrossOrigin
public class BoatController {
	
	@Autowired
	private BoatService cottageService;

	@GetMapping(value = "/getAllOwnerCottages/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER') or hasRole('ADMIN')")
	public ResponseEntity<List<Boat>> getAllOwnerCottages(@PathVariable("ownerId") Long ownerId) {
		List<Boat> cottages = new ArrayList<Boat>();
		cottages = cottageService.findAll();
		List<Boat> cottages1 = new ArrayList<Boat>();
		for (Boat a : cottages) {
			if (a.getUserBoat().getId() == ownerId) {
				cottages1.add(a);
			}
		}
		return new ResponseEntity<>(cottages1, HttpStatus.OK);
	}
	

	@PostMapping(value = "/subscribe", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<EntitySubscriber> subscribe(@RequestBody EntitySubscriber es) {
		if (es.getBoat().getId() == null || es.getSubscriber().getId() == null) {
			System.out.println("Some fields are empty.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			cottageService.subscribe(es.getBoat().getId(), es.getSubscriber().getId());
			System.out.println("The task /subscribe was successfully completed.");
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	}
	
	@GetMapping(value = "/unsubscribe/{cottageId}/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<EntitySubscriber> unsubscribe(@PathVariable("cottageId") Long cottageId, @PathVariable("userId") Long userId) {
		if (cottageId == null || userId == null) {
			System.out.println("Some id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		cottageService.unsubscribe(cottageId, userId);
		System.out.println("The task /unsubscribe was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllSubscribers/{cottageId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EntitySubscriber>> getAllSubscribers(@PathVariable("cottageId") Long cottageId) {
		List<EntitySubscriber> subs = new ArrayList<>();
		subs = cottageService.findAllSubsByCottage(cottageId);
		System.out.println("The task /getAllSubs was successfully completed.");
		return new ResponseEntity<>(subs, HttpStatus.OK);
	}
	
	@PostMapping(value = "/createCottage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Boat> createCottage(@RequestBody Boat cottage1, @PathVariable("id") Long id) {
		if (cottage1.getName().isEmpty() || cottage1.getAddress().isEmpty() || cottage1.getType().isEmpty() || cottage1.getLenght().isEmpty() || cottage1.getEngineNumber().isEmpty() || cottage1.getEnginePower().isEmpty() || cottage1.getMaxSpeed().isEmpty() || cottage1.getNavigationEquipment().isEmpty() || cottage1.getCapacity().isEmpty() || cottage1.getDescription().isEmpty() || cottage1.getRules().isEmpty() || cottage1.getInfo().isEmpty() || cottage1.getImage().isEmpty() || cottage1.getFishingEquipment().isEmpty() || cottage1.getCancelTerms().isEmpty() || cottage1.getLatitude() == 0 || cottage1.getLongitude() == 0 ) {
			System.out.println("Some fields are empty.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			Boat cottage = cottageService.save(cottage1, id);
			System.out.println("The task /createCottage was successfully completed.");
			return new ResponseEntity<>(cottage, HttpStatus.CREATED);
		}
	}

	@GetMapping(value = "/deleteCottage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER') or hasRole('ADMIN')")
	public ResponseEntity<Boat> deleteCottage(@PathVariable("id") Long id) {
		if (id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		cottageService.delete(id);
		System.out.println("The task /deleteCottage was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllCottages", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Boat>> getAllCottages() {
		List<Boat> cottages = new ArrayList<Boat>();
		cottages = cottageService.findAll();
		return new ResponseEntity<>(cottages, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getCottageById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boat> getCottageById(@PathVariable("id") Long id) {
		List<Boat> cottages = new ArrayList<Boat>();
		cottages = cottageService.findAll();
		for (Boat c : cottages) {
			if (c.getId() == id) {
				System.out.println("The task /getCottage was successfully completed.");
				return new ResponseEntity<>(c, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@PutMapping(value = "/updateCottage", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Boat> updateCottage(@RequestBody Boat cottage1) throws Exception {
		Boat cottage = cottageService.findById(cottage1.getId());
		if (cottage1.getName().isEmpty() || cottage1.getAddress().isEmpty() || cottage1.getType().isEmpty() || cottage1.getLenght().isEmpty() || cottage1.getEngineNumber().isEmpty() || cottage1.getEnginePower().isEmpty() || cottage1.getMaxSpeed().isEmpty() || cottage1.getNavigationEquipment().isEmpty() || cottage1.getCapacity().isEmpty() || cottage1.getDescription().isEmpty() || cottage1.getRules().isEmpty() || cottage1.getInfo().isEmpty() || cottage1.getImage().isEmpty() || cottage1.getFishingEquipment().isEmpty() || cottage1.getCancelTerms().isEmpty() || cottage1.getLatitude() == 0 || cottage1.getLongitude() == 0 ) {
			System.out.println("Some fields are empty.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (cottage == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		cottageService.update(cottage1);
		System.out.println("The task /updateCottage was successfully completed.");
		return new ResponseEntity<>(cottage, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getAllCottageImages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BoatImage>> getAllCottageImages(@PathVariable("id") Long id) {
		List<BoatImage> images = new ArrayList<BoatImage>();
		images = cottageService.findImagesByCottageId(id);
		System.out.println("The task /getAllCottageImages was successfully completed.");
		return new ResponseEntity<>(images, HttpStatus.OK);
	}



	@PutMapping(value = "/updateImage", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatImage> updateImage(@RequestBody BoatImage image) throws Exception {
		BoatImage image1 = cottageService.findImageById(image.getId());
		if (image1== null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		cottageService.updateImage(image);
		System.out.println("The task /updateImage was successfully completed.");
		return new ResponseEntity<>(image, HttpStatus.OK);
	}
	
	@GetMapping(value = "/deleteImage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatImage> deleteImage(@PathVariable("id") Long id) {
		if (id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		cottageService.deleteImage(id);
		System.out.println("The task /deleteImage was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/saveImage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatImage> saveImage(@RequestBody String url, @PathVariable("id") Long id) throws Exception {
		
		if (cottageService.saveImage(url, id)) {
			System.out.println("Image saved!");
		} else {
			System.out.println("Image already exists!");
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
