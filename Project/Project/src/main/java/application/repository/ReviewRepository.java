package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
}
