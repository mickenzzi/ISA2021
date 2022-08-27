package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.BoatImage;

public interface BoatImageRepository extends JpaRepository<BoatImage, Long>{

}
