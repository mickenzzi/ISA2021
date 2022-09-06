package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.EntitySubscriber;

public interface EntitySubscriberRepository extends JpaRepository<EntitySubscriber, Long>{
	
}
