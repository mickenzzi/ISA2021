package application.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import application.model.Adventure;
import application.model.Image;
import application.model.Termin;
import application.service.AdventureService;
import application.service.UserService;

@RestController
@RequestMapping(value = "/api/adventures")
@CrossOrigin
public class AdventureController {
	@Autowired
	private AdventureService adventureService;
	@Autowired
	private UserService userService;

	@GetMapping(value = "/getAllAdventures/{instructorId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Adventure>> getAllAdventures(@PathVariable("instructorId") Long instructorId) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureService.findAll();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for (Adventure a : adventures) {
			if (a.getUserAdventure().getId() == instructorId) {
				adventures1.add(a);
			}
		}
		System.out.println("The task /getAllAdventures was successfully completed.");
		return new ResponseEntity<>(adventures1, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllAdventureImages/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Image>> getAllAdventureImages(@PathVariable("id") Long id) {
		List<Image> images = new ArrayList<Image>();
		images = adventureService.getAdventureImages(id);

		System.out.println("The task /getAllAdventures was successfully completed.");
		return new ResponseEntity<>(images, HttpStatus.OK);
	}

	@GetMapping(value = "/getSearchAdventures/{instructorId}/{search}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<List<Adventure>> getAllAdventures(@PathVariable("instructorId") Long instructorId,
			@PathVariable("search") String search) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureService.findAll();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		List<Adventure> searchedAdventures = new ArrayList<Adventure>();
		for (Adventure a : adventures) {
			if (a.getUserAdventure().getId() == instructorId) {
				adventures1.add(a);
			}
		}
		for (Adventure a1 : adventures1) {
			if (a1.getTitle().toLowerCase().contains(search.toLowerCase())
					|| a1.getAddress().toLowerCase().contains(search.toLowerCase())) {
				searchedAdventures.add(a1);
			}
		}
		System.out.println("The task /getSearchAdventures was successfully completed.");
		return new ResponseEntity<>(searchedAdventures, HttpStatus.OK);
	}

	@GetMapping(value = "/getAdventureById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<Adventure> getAdventureById(@PathVariable("id") Long id) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureService.findAll();
		for (Adventure a : adventures) {
			if (a.getId() == id) {
				System.out.println("The task /getAdventure was successfully completed.");
				return new ResponseEntity<>(a, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@PutMapping(value = "/updateAdventure", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<Adventure> updateAdventure(@RequestBody Adventure adventure1) throws Exception {
		Adventure adventure = adventureService.findById(adventure1.getId());
		if (adventure1.getAddress().isEmpty() || adventure1.getCancelCondition().isEmpty() || adventure1.getLatitude() == 0 || adventure1.getLongitude() == 0
				|| adventure1.getDescription().isEmpty() || adventure1.getEquipment().isEmpty()
				|| adventure1.getRule().isEmpty() || adventure1.getTitle().isEmpty()
				|| adventure1.getInstructorBiography().isEmpty()) {
			System.out.println("Some fields are empty.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (adventure == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		adventureService.updateAdventure(adventure1);
		System.out.println("The task /updateAdventure was successfully completed.");
		return new ResponseEntity<>(adventure, HttpStatus.OK);
	}
	

	@PutMapping(value = "/updateImage", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<Image> updateImage(@RequestBody Image image) throws Exception {
		Image image1 = adventureService.findImageById(image.getId());
		if (image1== null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		adventureService.updateImage(image);
		System.out.println("The task /updateImage was successfully completed.");
		return new ResponseEntity<>(image, HttpStatus.OK);
	}

	@PostMapping(value = "/createAdventure/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<Adventure> createAdventure(@RequestBody Adventure adventure1, @PathVariable("id") Long id) {
		if (adventure1.getAddress().isEmpty() || adventure1.getCancelCondition().isEmpty() || adventure1.getLatitude() == 0 || adventure1.getLongitude() == 0
				|| adventure1.getDescription().isEmpty() || adventure1.getEquipment().isEmpty()
				|| adventure1.getRule().isEmpty() || adventure1.getTitle().isEmpty()
				|| adventure1.getInstructorBiography().isEmpty()) {
			System.out.println("Some fields are empty.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			Adventure adventure = adventureService.createAdventure(adventure1, id);
			System.out.println("The task /createAdventure was successfully completed.");
			return new ResponseEntity<>(adventure, HttpStatus.CREATED);
		}
	}

	@GetMapping(value = "/deleteAdventure/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR') or hasRole('ADMIN')")
	public ResponseEntity<Adventure> deleteAdventure(@PathVariable("id") Long id) {
		if (id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		adventureService.delete(id);
		System.out.println("The task /deleteAdventure was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "/sortAdventuresByTitle/{instructorId}/{asc}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<List<Adventure>> sortAdventuresByTitle(@PathVariable("instructorId") Long instructorId,
			@PathVariable("asc") boolean asc) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureService.sortByTitle(instructorId, asc);
		System.out.println("The task /sortAdventuresByTitle was successfully completed.");
		return new ResponseEntity<>(adventures, HttpStatus.OK);
	}

	@GetMapping(value = "/sortAdventuresByPrice/{instructorId}/{asc}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<List<Adventure>> sortAdventuresByPrice(@PathVariable("instructorId") Long instructorId,
			@PathVariable("asc") boolean asc) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureService.sortByPrice(instructorId, asc);
		System.out.println("The task /sortAdventuresByPrice was successfully completed.");
		return new ResponseEntity<>(adventures, HttpStatus.OK);
	}

	@GetMapping(value = "/sortAdventuresByCapacity/{instructorId}/{asc}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<List<Adventure>> sortAdventuresByCapacity(@PathVariable("instructorId") Long instructorId,
			@PathVariable("asc") boolean asc) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureService.sortByCapacity(instructorId, asc);
		System.out.println("The task /sortAdventuresByCapacity was successfully completed.");
		return new ResponseEntity<>(adventures, HttpStatus.OK);
	}

	@GetMapping(value = "/sortAdventuresByGrade/{instructorId}/{asc}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<List<Adventure>> sortAdventuresGrade(@PathVariable("instructorId") Long instructorId,
			@PathVariable("asc") boolean asc) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureService.sortByGrade(instructorId, asc);
		System.out.println("The task /sortAdventuresByGrade was successfully completed.");
		return new ResponseEntity<>(adventures, HttpStatus.OK);
	}

	@PostMapping(value = "/createAction/{instructorId}/{adventureId}/{price}/{capacity}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<Termin> createAction(@RequestBody Termin termin,
			@PathVariable("instructorId") Long instructorId, @PathVariable("adventureId") Long adventureId,
			@PathVariable("price") Double price, @PathVariable("capacity") Long capacity) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss");
		boolean check = true;
		try {
			Date start = dateFormat.parse(termin.getStart());
			Date end = dateFormat.parse(termin.getEnd());
			check = true;
		} catch (Exception e) {
			check = false;
		}
		if (check == true) {
			if (termin.getDuration() != 0) {
				boolean flag = userService.createAction(instructorId, adventureId, termin, price, capacity);
				if (flag == true) {
					System.out.println("The task /createAction was successfully completed.");
					return new ResponseEntity<>(termin, HttpStatus.CREATED);
				} else {
					System.out.println("Termin is already used.");
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			} else {
				System.out.println("Some fields are empty.");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			System.out.println("Dates are not in appropriate format.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
