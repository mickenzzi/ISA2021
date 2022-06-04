package application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import application.model.Adventure;
import application.model.Complaint;
import application.repository.AdventureRepository;
import application.repository.ComplaintRepository;
import application.repository.UserRepository;
import application.service.ComplaintService;
import application.model.User;

@Service
public class ComplaintServiceImpl implements ComplaintService{

	@Autowired
	private ComplaintRepository complaintRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdventureRepository adventureRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public Complaint saveComplaint(Complaint complaint) {
		return complaintRepository.save(complaint);
	}

	@Override
	public Complaint findById(Long id) {
		return complaintRepository.findById(id).orElseGet(null);
	}

	@Override
	public Complaint create(Complaint complaint, Long userId, Long adventureId) {
		Complaint complaint1 = new Complaint();
		complaint1.setContent(complaint.getContent());
		User user = userRepository.findById(userId).orElseGet(null);
		Adventure adventure = adventureRepository.findById(adventureId).orElseGet(null);
		User instructor = userRepository.findById(adventure.getUserAdventure().getId()).orElseGet(null);
		complaint1.setUserComplaint(user);
		complaint1.setInstructorComplaint(instructor);
		complaint1.setAnswered(false);
		complaintRepository.save(complaint1);
		return complaint1;
		
	}

	@Override
	public List<Complaint> findAll() {
		return complaintRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		Complaint complaint = complaintRepository.findById(id).orElseGet(null);
		complaintRepository.delete(complaint);
	}

	@Override
	public Complaint answer(Complaint complaint, Long adminId, Long complaintId) {
		Complaint answer = new Complaint();
		Complaint complaint1 = complaintRepository.findById(complaintId).orElseGet(null);
		User user = userRepository.findById(complaint1.getUserComplaint().getId()).orElseGet(null);
		User instructor = userRepository.findById(complaint1.getInstructorComplaint().getId()).orElseGet(null);
		User admin = userRepository.findById(adminId).orElseGet(null);
		answer.setContent(complaint.getContent());
		answer.setAdminComplaint(admin);
		answer.setInstructorComplaint(instructor);
		answer.setUserComplaint(user);
		answer.setAnswered(true);
		complaint1.setAnswered(true);
		SimpleMailMessage mail1 = new SimpleMailMessage();
		SimpleMailMessage mail2 = new SimpleMailMessage();
		mail1.setTo(user.getEmail());
		mail2.setTo(instructor.getEmail());
		mail1.setFrom(admin.getEmail());
		mail2.setFrom(admin.getEmail());
		mail1.setSubject("Odgovor na zalbu");
		mail2.setSubject("Odgovor na zalbu");
		mail1.setText(complaint.getContent());
		mail2.setText(complaint.getContent());
		javaMailSender.send(mail1);
		javaMailSender.send(mail2);
		complaintRepository.save(complaint1);
		complaintRepository.save(answer);
		return answer;
	}

}
