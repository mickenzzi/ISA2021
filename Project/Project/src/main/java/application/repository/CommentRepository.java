package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
