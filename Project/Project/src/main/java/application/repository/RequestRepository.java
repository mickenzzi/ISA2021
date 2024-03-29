package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long>{
	Request findByUsername(String username);
}
