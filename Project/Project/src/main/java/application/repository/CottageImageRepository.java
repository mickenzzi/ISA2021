package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.CottageImage;

public interface CottageImageRepository extends JpaRepository<CottageImage, Long>{

}
