package application.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import application.model.OwnerReport;
import application.model.User;
import application.repository.OwnerReportRepository;
import application.repository.UserRepository;
import application.service.OwnerReportService;
import application.service.UserService;

@Service
public class OwnerReportServiceImpl implements OwnerReportService {

	@Autowired
	private OwnerReportRepository reportRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private Environment env;
	
	@Override
	public OwnerReport create(OwnerReport report) {
		System.out.println("Sankcije: " + report.isSanctioned() + " approved: " + report.isApproved() + " missed term: " + report.isMissedTerm());
		OwnerReport rep = new OwnerReport();
		rep.setComment(report.getComment());
		rep.setApproved(report.isApproved());
		rep.setSanctioned(report.isSanctioned());
		rep.setTerm(report.getTerm());
		rep.setMissedTerm(report.isMissedTerm());
		rep.setOwner(report.getOwner());
		reportRepository.save(rep);
		
		if(rep.isMissedTerm()) {
			User user = userService.findById(report.getTerm().getUserReserved().getId());
			user.setPenalty(user.getPenalty()+1);
			userRepository.save(user);
			
			User owner = userService.findById(report.getOwner().getId());
			
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(user.getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Dobili ste kaznu"); 
			mail.setText("Dobili ste 1 kazneni poen zato sto se niste pojavili u vreme rezervacije."); 
			javaMailSender.send(mail);
			
			mail.setTo(owner.getEmail());
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject("Kazna je dodeljena"); 
			mail.setText("Korisnik " + user.getFirstName() + " " + user.getLastName() + " kaznjen je po vasem zahtevu."); 
			javaMailSender.send(mail);
		}
		
		
		
		return rep;
	}

	@Override
	public void delete(Long reportId) {
		OwnerReport report = GetById(reportId);
		reportRepository.delete(report);
		
	}

	@Override
	public void update(OwnerReport report) {
		OwnerReport rep = GetById(report.getId());
		rep.setComment(report.getComment());
		rep.setApproved(report.isApproved());
		rep.setSanctioned(report.isSanctioned());
		rep.setTerm(report.getTerm());
		rep.setMissedTerm(report.isMissedTerm());
		rep.setOwner(report.getOwner());
		reportRepository.save(rep);
	}

	@Override
	public List<OwnerReport> GetAll() {
		return reportRepository.findAll();
	}
	

	@Override
	public OwnerReport GetById(Long id) {
		return reportRepository.findById(id).orElseGet(null);
	}

	@Override
	public List<OwnerReport> getUnapprovedReports() {
		
		List<OwnerReport> allReports = new ArrayList<>();
		allReports = reportRepository.findAll();
		List<OwnerReport> returnReports = new ArrayList<>();

		for(OwnerReport r : allReports) {
			if(!r.isApproved() && r.isSanctioned()) {
				returnReports.add(r);
			}
		}
		
		return returnReports;
	}



}
