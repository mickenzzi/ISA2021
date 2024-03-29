package application.service;

import java.util.List;

import application.model.Comment;

public interface CommentService {
	Comment saveComment(Comment comment);
	Comment findById(Long id);
	Comment create(Comment comment,Long userId,Long instructorId);
	List<Comment> findAll();
	void delete(Long id);
	Comment enableComment(Long id);
}
