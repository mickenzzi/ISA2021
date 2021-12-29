package application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.Request;
import application.service.RequestService;

@RestController
@RequestMapping(value = "/api/requests")

public class RequestController {
	@Autowired
	private RequestService requestService;
	
	@GetMapping(value = "/getAllRequests/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Request>> getAllRequests(@PathVariable("id") Long id) {
		List<Request> requests = requestService.findAll(id);
		List<Request> requests1 = new ArrayList<Request>();
		for(Request r: requests) {
			if(r.isDeleted()==false) {
				requests1.add(r);
			}
		}
		System.out.println("The task /getAllRequest was successfully completed.");
		return new ResponseEntity<>(requests1, HttpStatus.OK);
	}
}
