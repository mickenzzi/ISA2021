package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Loyalty;

public interface LoyaltyRepository extends JpaRepository<Loyalty, Long> {
	Loyalty findByName(String name);
}
