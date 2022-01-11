package application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.Termin;
import application.service.TerminService;

@RestController
@RequestMapping(value = "/api/termins")
public class TerminController {
	@Autowired
	private TerminService terminService;

	@GetMapping(value = "/getAllTerminsInstructor/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Termin>> getAllTerminsInstructor(@PathVariable("id") Long id) {
		List<Termin> termins = new ArrayList<Termin>();
		termins = terminService.findAllTerminsInstructor(id);
		System.out.println("The task /getAllTermins was successfully completed.");
		return new ResponseEntity<>(termins, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getFreeTerminsInstructor{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Termin>> getFreeTerminsInstructor(@PathVariable("id") Long id) {
		List<Termin> termins = new ArrayList<Termin>();
		termins = terminService.findFreeTerminsInstructor(id);
		System.out.println("The task /getAllTermins was successfully completed.");
		return new ResponseEntity<>(termins, HttpStatus.OK);
	}

	@GetMapping(value = "/getTerminById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Termin> getTerminById(@PathVariable("id") Long id) {
		Termin termin = terminService.findById(id);
		if (termin == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			System.out.println("The task /getTermin was successfully completed.");
			return new ResponseEntity<>(termin, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/updateTermin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Termin> updateTermin(@RequestBody Termin termin1) throws Exception {
		Termin termin = terminService.findById(termin1.getId());
		if (termin == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		terminService.updateTermin(termin1);
		System.out.println("The task /updateTermin was successfully completed.");
		return new ResponseEntity<>(termin, HttpStatus.OK);
	}

	@PostMapping(value = "/createTermin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Termin> createTermin(@RequestBody Termin termin1) {

		Termin termin = terminService.createTermin(termin1);
		System.out.println("The task /createTermin was successfully completed.");
		return new ResponseEntity<>(termin, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/deleteTermin/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Termin> deleteTermin(@PathVariable("id") Long id) {
		if (id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		terminService.delete(id);
		System.out.println("The task /deleteTermin was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
