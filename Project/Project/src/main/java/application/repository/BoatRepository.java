package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Boat;

public interface BoatRepository extends JpaRepository<Boat, Long>{

}