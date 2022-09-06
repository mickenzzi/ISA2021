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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.model.OwnerReport;
import application.service.OwnerReportService;

@RestController
@RequestMapping(value = "/api/reports")
public class OwnerReportController {
	
	@Autowired
	OwnerReportService reportService;
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER') or hasRole('BOAT_OWNER')")
	public ResponseEntity<OwnerReport> create(@RequestBody OwnerReport report) {
		if (report.getComment().equals("") || report.getComment() == null || report.getTerm() == null) {
			System.out.println("Some fields are empty.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			OwnerReport report1 = reportService.create(report);
			System.out.println("The task /createReport was successfully completed.");
			return new ResponseEntity<>(report1, HttpStatus.CREATED);
		}
	}

	@GetMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER') or hasRole('ADMIN') or hasRole('BOAT_OWNER')")
	public ResponseEntity<OwnerReport> delete(@PathVariable("id") Long id) {
		if (id == null) {
			System.out.println("Id is null.");
			return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
		}
		reportService.delete(id);
		System.out.println("The task /deleteReport was successfully completed.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OwnerReport>> getAll() {
		List<OwnerReport> reports = new ArrayList<>();
		reports = reportService.GetAll();
		return new ResponseEntity<>(reports, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getUnapproved", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<OwnerReport>> getUnapproved() {
		List<OwnerReport> reports = new ArrayList<>();
		reports = reportService.getUnapprovedReports();
		return new ResponseEntity<>(reports, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OwnerReport> getById(@PathVariable("id") Long id) {
		List<OwnerReport> reports = new ArrayList<>();
		reports = reportService.GetAll();
		for (OwnerReport rep : reports) {
			if (rep.getId() == id) {
				System.out.println("The task /getById was successfully completed.");
				return new ResponseEntity<>(rep, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('COTTAGE_OWNER') or hasRole('BOAT_OWNER')")
	public ResponseEntity<OwnerReport> update(@RequestBody OwnerReport report1) throws Exception {
		OwnerReport report = reportService.GetById(report1.getId());
		if (report.getComment().equals("") || report.getComment() == null || report.getTerm() == null) {
			System.out.println("Some fields are empty.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (report == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		reportService.update(report1);
		System.out.println("The task /updateReport was successfully completed.");
		return new ResponseEntity<>(report, HttpStatus.OK);
	}
	
}
