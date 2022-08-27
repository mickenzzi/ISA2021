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
import application.service.BoatService;

@RestController
@RequestMapping(value = "/api/boats")
@CrossOrigin
public class BoatController {
	
	@Autowired
	private BoatService boatService;

	@GetMapping(value = "/getAllOwnerBoats/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER') or hasRole('ADMIN')")
	public ResponseEntity<List<Boat>> getAllOwnerBoats(@PathVariable("ownerId") Long ownerId) {
		List<Boat> boats = new ArrayList<Boat>();
		boats = boatService.findAll();
		List<Boat> boats1 = new ArrayList<Boat>();
		for (Boat a : boats) {
			if (a.getUserBoat().getId() == ownerId) {
				boats1.add(a);
			}
		}
		return new ResponseEntity<>(boats1, HttpStatus.OK);
	}
	

	@PostMapping(value = "/createBoat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Boat> createBoat(@RequestBody Boat boat1, @PathVariable("id") Long id) {
		if (boat1.getName().isEmpty() || boat1.getType().isEmpty() || boat1.getLenght().isEmpty() || boat1.getMaxSpeed().isEmpty() || boat1.getEnginePower().isEmpty() || boat1.getNavigationEquipment().isEmpty() || boat1.getAddress().isEmpty() || boat1.getDescription().isEmpty() || boat1.getImage().isEmpty() || boat1.getCapacity().isEmpty() || boat1.getRules().isEmpty() || boat1.getFishingEquipment().isEmpty() || boat1.getPrice() == 0 || boat1.getInfo().isEmpty()) {
			System.out.println("Some fields are empty.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			Boat boat = boatService.save(boat1, id);
			System.out.println("The task /createBoat was successfully completed.");
			return new ResponseEntity<>(boat, HttpStatus.CREATED);
		}
	}

	@GetMapping(value = "/deleteBoat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER') or hasRole('ADMIN')")
	public ResponseEntity<Boat> deleteBoat(@PathVariable("id") Long id) {
		if (id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		boatService.delete(id);
		System.out.println("The task /deleteBoat was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllBoats", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Boat>> getAllBoats() {
		List<Boat> boats = new ArrayList<Boat>();
		boats = boatService.findAll();
		return new ResponseEntity<>(boats, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getBoat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boat> getBoat(@PathVariable("id") Long id) {
		List<Boat> boats = new ArrayList<Boat>();
		boats = boatService.findAll();
		for (Boat b : boats) {
			if (b.getId() == id) {
				System.out.println("The task /getBoat was successfully completed.");
				return new ResponseEntity<>(b, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@PutMapping(value = "/updateBoat", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<Boat> updateCottage(@RequestBody Boat boat1) throws Exception {
		Boat boat = boatService.findById(boat1.getId());
		if (boat1.getName().isEmpty() || boat1.getType().isEmpty() || boat1.getLenght().isEmpty() || boat1.getMaxSpeed().isEmpty() || boat1.getEnginePower().isEmpty() || boat1.getNavigationEquipment().isEmpty() || boat1.getAddress().isEmpty() || boat1.getDescription().isEmpty() || boat1.getImage().isEmpty() || boat1.getCapacity().isEmpty() || boat1.getRules().isEmpty() || boat1.getFishingEquipment().isEmpty() || boat1.getPrice() == 0 || boat1.getInfo().isEmpty()) {
			System.out.println("Some fields are empty.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (boat == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		boatService.update(boat1);
		System.out.println("The task /updateCottage was successfully completed.");
		return new ResponseEntity<>(boat, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getAllBoatImages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BoatImage>> getAllBoatImages(@PathVariable("id") Long id) {
		List<BoatImage> images = new ArrayList<BoatImage>();
		images = boatService.findImagesByBoatId(id);
		System.out.println("The task /getAllBoatImages was successfully completed.");
		return new ResponseEntity<>(images, HttpStatus.OK);
	}



	@PutMapping(value = "/updateImage", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatImage> updateImage(@RequestBody BoatImage image) throws Exception {
		BoatImage image1 = boatService.findImageById(image.getId());
		if (image1== null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		boatService.updateImage(image);
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
		boatService.deleteImage(id);
		System.out.println("The task /deleteImage was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping(value = "/saveImage/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BOAT_OWNER')")
	public ResponseEntity<BoatImage> saveImage(@RequestBody String url, @PathVariable("id") Long id) throws Exception {
		
		if (boatService.saveImage(url, id)) {
			System.out.println("Image saved!");
		} else {
			System.out.println("Image already exists!");
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
