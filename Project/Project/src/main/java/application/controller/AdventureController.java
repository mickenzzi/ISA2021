package application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.Adventure;
import application.service.AdventureService;

@RestController
@RequestMapping(value = "/api/adventures")
public class AdventureController {
	@Autowired
	private AdventureService adventureService;

	@GetMapping(value = "/getAllAdventures/{instructorId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Adventure>> getAllAdventures(@PathVariable("instructorId") Long instructorId) {
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureService.findAll();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for(Adventure a:adventures) {
			if(a.getUserAdventure().getId()==instructorId) {
				adventures1.add(a);
			}
		}
		System.out.println("The task /getAllAdventures was successfully completed.");
		return new ResponseEntity<>(adventures1, HttpStatus.OK);
	}

	@GetMapping(value = "/getAdventureById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Adventure> getAdventureById(@PathVariable("id") Long id) {
		Adventure adventure = adventureService.findById(id);
		if (adventure == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			System.out.println("The task /getAdventure was successfully completed.");
			return new ResponseEntity<>(adventure, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/updateAdventure", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Adventure> updateAdventure(@RequestBody Adventure adventure1) throws Exception {
		Adventure adventure = adventureService.findById(adventure1.getId());
		if (adventure == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		adventureService.updateAdventure(adventure1);
		System.out.println("The task /updateAdventure was successfully completed.");
		return new ResponseEntity<>(adventure, HttpStatus.OK);
	}

	@PostMapping(value = "/createAdventure/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Adventure> createAdventure(@RequestBody Adventure adventure1,@PathVariable("id") Long id) {
		Adventure adventure = adventureService.createAdventure(adventure1,id);
		System.out.println("The task /createAdventure was successfully completed.");
		return new ResponseEntity<>(adventure, HttpStatus.CREATED);

	}
	
	@GetMapping(value = "/deleteAdventure/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Adventure> deleteAdventureAdventure(@PathVariable("id") Long id) {
		if(id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		adventureService.delete(id);
		System.out.println("The task /createAdventure was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);

	}

}
