package application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	//user id
	@GetMapping(value = "/getAllRequests/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Request>> getAllRequests(@PathVariable("id") Long id) {
		List<Request> requests = requestService.findAll(id);
		List<Request> requests1 = new ArrayList<Request>();
		for(Request r: requests) {
			requests1.add(r);
		}
		System.out.println("The task /getAllRequest was successfully completed.");
		return new ResponseEntity<>(requests1, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getRequest/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Request>> getRequest(@PathVariable("id") Long id){
		Request request = requestService.findById(id);
		List<Request> requests = new ArrayList<Request>();
		requests.add(request);
		System.out.println("The task /getRequest was successfully completed.");
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "/createRequest/{userId}/{text}", produces = MediaType.APPLICATION_JSON_VALUE )
	@PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR') or hasRole('USER')")
	public ResponseEntity<Request> createRequest(@PathVariable("userId") Long userId,@PathVariable("text") String text) {
		Request request1 = requestService.createRequest(userId,text);
		System.out.println("The task /createRequest was successfully completed.");
		return new ResponseEntity<>(request1, HttpStatus.CREATED);
	}
	
}
