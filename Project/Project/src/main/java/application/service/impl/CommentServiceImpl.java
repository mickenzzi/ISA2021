package application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import application.model.Comment;
import application.model.User;
import application.repository.CommentRepository;
import application.repository.UserRepository;
import application.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public Comment saveComment(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public Comment findById(Long id) {
		return commentRepository.findById(id).orElseGet(null);
	}

	@Override
	public Comment create(Comment comment,Long userId,Long instructorId) {
		Comment comment1 = new Comment();
		comment1.setContent(comment.getContent());
		comment1.setNegative(comment.isNegative());
		if(comment.isNegative() == true) {
			comment1.setEnabled(false);
		}
		else {
			comment1.setEnabled(true);
		}
		User user = userRepository.findById(userId).orElseGet(null);
		User instructor = userRepository.findById(instructorId).orElseGet(null);
		comment1.setInstructorComment(instructor);
		comment1.setUserComment(user);
		commentRepository.save(comment1);
		return comment1;
	}

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		Comment comment = commentRepository.findById(id).orElseGet(null);
		commentRepository.delete(comment);
	}

	@Override
	public Comment enableComment(Long id) {
		Comment comment = commentRepository.findById(id).orElseGet(null);
		User user = userRepository.findById(comment.getUserComment().getId()).orElseGet(null);
		User instructor = userRepository.findById(comment.getInstructorComment().getId()).orElseGet(null);
		User admin = userRepository.findByUsername("mickenzi");
		comment.setEnabled(true);
		commentRepository.save(comment);
		if(comment.isNegative() == true) {
			user.setPenalty(user.getPenalty()+1);
			if(user.getPenalty() >= 5) {
				if(user.getLoyaltyStatus().toLowerCase().equals("gold")) {
					user.setLoyaltyStatus("SILVER");
				}
				else if(user.getLoyaltyStatus().toLowerCase().equals("silver")) {
					user.setLoyaltyStatus("BRONZE");
				}
				user.setPenalty(0);
			}
			userRepository.save(user);
			SimpleMailMessage mail1 = new SimpleMailMessage();
			mail1.setTo(user.getEmail());
			mail1.setFrom(admin.getEmail());
			mail1.setSubject("Komentar");
			mail1.setText("Instruktor je uneo negativan komentar o vama.");
			javaMailSender.send(mail1);
			SimpleMailMessage mail2 = new SimpleMailMessage();
			mail2.setTo(instructor.getEmail());
			mail2.setFrom(admin.getEmail());
			mail2.setSubject("Komentar");
			mail2.setText("Vas negativan komentar je prosao validaciju admina.");
			javaMailSender.send(mail2);
		}
		return comment;
	}

}
