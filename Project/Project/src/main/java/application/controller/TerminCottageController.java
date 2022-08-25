package application.controller;

import java.text.ParseException;
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

import application.model.Termin;
import application.model.TerminCottage;
import application.service.TerminCottageService;

@RestController
@RequestMapping(value = "/api/cottageTermins")
@CrossOrigin
public class TerminCottageController {

	@Autowired
	private TerminCottageService terminCottageService;
	
	@PostMapping(value = "/createTermin/{cottageId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_COTTAGE_OWNER')")
	public ResponseEntity<?> createTermin(@PathVariable("cottageId") Long cottageId, @RequestBody TerminCottage termin)
			throws ParseException {

		boolean create = terminCottageService.createTermin(termin, cottageId);
		if (create) {
			System.out.println("The task /createTermin was successfully completed.");
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			System.out.println("The termin already exist.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/getAllTermins/{cottageId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TerminCottage>> getAllCottageTermins(@PathVariable("cottageId") Long cottageId) {
		List<TerminCottage> termins = new ArrayList<>();
		termins = terminCottageService.findAllTerminsByCottage(cottageId);
		System.out.println("The task /getAllTermins was successfully completed.");
		return new ResponseEntity<>(termins, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getTermin/{cottageId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TerminCottage> getTermin(@PathVariable("cottageId") Long cottageId) {
		TerminCottage termin = new TerminCottage();
		termin = terminCottageService.findById(cottageId);
		System.out.println("The task /getAllTermins was successfully completed.");
		return new ResponseEntity<>(termin, HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateTermin", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TerminCottage> updateTermin(@RequestBody TerminCottage termin) {
		if(terminCottageService.updateTermin(termin)) {
			System.out.println("The task /getAllTermins was successfully completed.");
			return new ResponseEntity<>(termin, HttpStatus.OK);
		}
		else {
			System.out.println("Termin update failed.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
