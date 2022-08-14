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

import application.model.Cottage;
import application.model.CottageImage;
import application.service.CottageService;
import application.service.UserService;

@RestController
@RequestMapping(value = "/api/cottages")
@CrossOrigin
public class CottageController {
	@Autowired
	private CottageService cottageService;
	@Autowired
	private UserService userService;

	@GetMapping(value = "/getAllOwnerCottages/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER') or hasRole('ADMIN')")
	public ResponseEntity<List<Cottage>> getAllOwnerCottages(@PathVariable("ownerId") Long ownerId) {
		List<Cottage> cottages = new ArrayList<Cottage>();
		cottages = cottageService.findAll();
		List<Cottage> cottages1 = new ArrayList<Cottage>();
		for (Cottage a : cottages) {
			if (a.getUserCottage().getId() == ownerId) {
				cottages1.add(a);
			}
		}
		return new ResponseEntity<>(cottages1, HttpStatus.OK);
	}
	

	@PostMapping(value = "/createCottage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Cottage> createAdventure(@RequestBody Cottage cottage1, @PathVariable("id") Long id) {
		if (cottage1.getName().isEmpty() || cottage1.getAddress().isEmpty() || cottage1.getDescription().isEmpty() || cottage1.getImage().isEmpty() || cottage1.getNumberOfBeds() == 0 || cottage1.getNumberOfRooms() == 0 || cottage1.getRules().isEmpty() || cottage1.getInfo().isEmpty()) {
			System.out.println("Some fields are empty.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			Cottage cottage = cottageService.save(cottage1, id);
			System.out.println("The task /createCottage was successfully completed.");
			return new ResponseEntity<>(cottage, HttpStatus.CREATED);
		}
	}

	@GetMapping(value = "/deleteCottage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER') or hasRole('ADMIN')")
	public ResponseEntity<Cottage> deleteCottage(@PathVariable("id") Long id) {
		if (id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		cottageService.delete(id);
		System.out.println("The task /deleteCottage was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllCottages", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cottage>> getAllCottages() {
		List<Cottage> cottages = new ArrayList<Cottage>();
		cottages = cottageService.findAll();
		return new ResponseEntity<>(cottages, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getCottageById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cottage> getCottageById(@PathVariable("id") Long id) {
		List<Cottage> cottages = new ArrayList<Cottage>();
		cottages = cottageService.findAll();
		for (Cottage a : cottages) {
			if (a.getId() == id) {
				System.out.println("The task /getCottage was successfully completed.");
				return new ResponseEntity<>(a, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@PutMapping(value = "/updateCottage", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Cottage> updateCottage(@RequestBody Cottage cottage1) throws Exception {
		Cottage cottage = cottageService.findById(cottage1.getId());
		if (cottage1.getName().isEmpty() || cottage1.getAddress().isEmpty() || cottage1.getDescription().isEmpty() || cottage1.getImage().isEmpty() || cottage1.getNumberOfBeds() == 0 || cottage1.getNumberOfRooms() == 0 || cottage1.getRules().isEmpty() || cottage1.getInfo().isEmpty()) {
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
	public ResponseEntity<List<String>> getAllCottageImages(@PathVariable("id") Long id) {
		List<CottageImage> images = new ArrayList<CottageImage>();
		images = cottageService.findImagesByCottageId(id);
		List<String> strImages = new ArrayList<String>();
		for(CottageImage image : images) {
			strImages.add(image.getImageUrl());
		}
		System.out.println("The task /getAllCottageImages was successfully completed.");
		return new ResponseEntity<>(strImages, HttpStatus.OK);
	}



	@PutMapping(value = "/updateImage", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageImage> updateImage(@RequestBody CottageImage image) throws Exception {
		CottageImage image1 = cottageService.findImageById(image.getId());
		if (image1== null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		cottageService.updateImage(image);
		System.out.println("The task /updateImage was successfully completed.");
		return new ResponseEntity<>(image, HttpStatus.OK);
	}
	
	@PostMapping(value = "/saveImage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<CottageImage> saveImage(@RequestBody String url, @PathVariable("id") Long id) throws Exception {
		
		if (cottageService.saveImage(url, id)) {
			System.out.println("Image saved!");
		} else {
			System.out.println("Image already exists!");
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}


}
