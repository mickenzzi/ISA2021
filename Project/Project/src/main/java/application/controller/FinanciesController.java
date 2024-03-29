package application.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.service.FinanciesService;

@RestController
@RequestMapping(value = "/api/financies")
@CrossOrigin
public class FinanciesController {
	@Autowired
	private FinanciesService financiesService;

	@GetMapping(value = "/getYearProfit/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getYearProfit(@PathVariable("year") String year) throws ParseException {
		Double profit = financiesService.getYearProfit(year);
		DecimalFormat df = new DecimalFormat("#.##");
		String realProfit = df.format(profit);
		System.out.println("The task /getYearProfit was successfully completed.");
		return new ResponseEntity<>(realProfit, HttpStatus.OK);
	}

	@GetMapping(value = "/getYearPerMonthProfit/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getYearPerMonthProfit(@PathVariable("year") String year) throws ParseException {
		List<Double> profits = financiesService.getMonthProfit(year);
		System.out.println("The task /getYearPerMonthProfit was successfully completed.");
		return new ResponseEntity<>(profits, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getReservationsPerMonth/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<?> getInstructorReservationPerMonth(@PathVariable("id") Long id) throws ParseException {
		List<Double> reservations = financiesService.getReservationsPerMonth(id);
		System.out.println("The task /getReservationsPerMonth was successfully completed.");
		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getReservationsPerWeek/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<?> getInstructorReservationPerWeek(@PathVariable("id") Long id) throws ParseException {
		List<Double> reservations = financiesService.getReservationsPerWeek(id);
		System.out.println("The task /getReservationsPerWeek was successfully completed.");
		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getReservationsPerDay/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<?> getInstructorReservationPerDay(@PathVariable("id") Long id) throws ParseException {
		List<Double> reservations = financiesService.getReservationsPerDay(id);
		System.out.println("The task /getReservationsPerWeek was successfully completed.");
		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}


	@GetMapping(value = "/getPercent", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getPercent() {
		double percent = financiesService.getPercent();
		System.out.println("The task /getPercent was successfully completed.");
		return new ResponseEntity<>(percent, HttpStatus.OK);
	}

	@GetMapping(value = "/getInstructorProfit/{id}/{start}/{end}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('INSTRUCTOR')")
	public ResponseEntity<?> getInstructorProfit(@PathVariable("id") Long id, @PathVariable("start") String start,
			@PathVariable("end") String end) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		boolean check = false;
		try {
			Date startDate = dateFormat.parse(start);
			Date endDate = dateFormat.parse(end);
			check = true;
		} catch (Exception e) {
			check = false;
		}
		if (check == false) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			Double profit = financiesService.getInstructorProfit(id, start, end);
			DecimalFormat df = new DecimalFormat("#.##");
			String realProfit = df.format(profit);
			System.out.println("The task /getInstructorProfit was successfully completed.");
			return new ResponseEntity<>(realProfit, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/editPercent", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Double> editPercent(@RequestBody String percent) {
		financiesService.editPercent(Double.parseDouble(percent));
		System.out.println("The task /editPercent was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
