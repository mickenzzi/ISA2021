package application.controller;

import java.text.ParseException;
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

import application.service.CottageBusinessService;

@RestController
@RequestMapping(value = "/api/cottageBusiness")
public class CottageBusinessController {

	@Autowired
	private CottageBusinessService businessService;
	
	@GetMapping(value = "/profit/{id}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<Double> getOwnerProfit(@PathVariable ("id") Long id, @PathVariable ("start") String start, @PathVariable ("end") String end) throws ParseException{
		if(start.length()!=20 || end.length()!=20)
		{
			System.out.println("Datumi nisu validni.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else if (id == null) {
			System.out.println("Id je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			Double profit = businessService.getOwnerProfit(start, end, id);
			System.out.println("Got profits!");
			return new ResponseEntity<>(profit, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/reservationsPerMonth/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<List<Integer>> numberOfReservationsOnMonth(@PathVariable ("id") Long id) throws ParseException{
		if (id == null) {
			System.out.println("Id je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			List<Integer> reservations = businessService.numberOfReservationsOnMonth(id);
			System.out.println("Got monthly reservations!");
			return new ResponseEntity<>(reservations, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/reservationsPerWeek/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<List<Integer>> numberOfReservationsOnWeek(@PathVariable ("id") Long id) throws ParseException{
		if (id == null) {
			System.out.println("Id je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			List<Integer> reservations = businessService.numberOfReservationsOnWeek(id);
			System.out.println("Got weekly reservations!");
			return new ResponseEntity<>(reservations, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/reservationsPerDay/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<List<Integer>> numberOfReservationsOnDay(@PathVariable ("id") Long id) throws ParseException{
		if (id == null) {
			System.out.println("Id je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			List<Integer> reservations = businessService.numberOfReservationsOnDay(id);
			System.out.println("Got daily reservations!");
			return new ResponseEntity<>(reservations, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/reservedDaysPerMonth/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<List<Integer>> numberOfReservedDaysPerMonth(@PathVariable ("id") Long id) throws ParseException{
		if (id == null) {
			System.out.println("Id je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			List<Integer> reservations = businessService.numberOfReservedDaysPerMonth(id);
			System.out.println("Got number of reserved days per month!");
			return new ResponseEntity<>(reservations, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/reservedDaysPerWeek/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<List<Integer>> numberOfReservedDaysPerWeek(@PathVariable ("id") Long id) throws ParseException{
		if (id == null) {
			System.out.println("Id je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			System.out.println("Started getting reserved days per week...");
			List<Integer> reservations = businessService.numberOfReservedDaysPerWeek(id);
			System.out.println("Got number of reserved days per week!");
			return new ResponseEntity<>(reservations, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/reservedDays/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<List<Integer>> numberOfReservedDays(@PathVariable ("id") Long id) throws ParseException{
		if (id == null) {
			System.out.println("Id je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			List<Integer> reservations = businessService.numberOfReservedDays(id);
			System.out.println("Got number of reserved days!");
			return new ResponseEntity<>(reservations, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/monthlyProfit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<List<Double>> profitPerMonth(@PathVariable ("id") Long id) throws ParseException{
		if (id == null) {
			System.out.println("Id je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			List<Double> reservations = businessService.profitPerMonth(id);
			System.out.println("Got monthly profits!");
			return new ResponseEntity<>(reservations, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/weeklyProfit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<List<Double>> profitPerWeek(@PathVariable ("id") Long id) throws ParseException{
		if (id == null) {
			System.out.println("Id je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			List<Double> reservations = businessService.profitPerWeek(id);
			System.out.println("Got weekly profits!");
			return new ResponseEntity<>(reservations, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/dailyProfit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER')")
	public ResponseEntity<List<Double>> profitPerDay(@PathVariable ("id") Long id) throws ParseException{
		if (id == null) {
			System.out.println("Id je null");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			List<Double> reservations = businessService.profitPerDay(id);
			System.out.println("Got daily profits!");
			return new ResponseEntity<>(reservations, HttpStatus.OK);
		}
	}
}
