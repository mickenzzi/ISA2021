package application.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.TerminCottage;
import application.service.TerminCottageService;

@RestController
@RequestMapping(value = "/api/cottageTermins")
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
	
	
	
}
