package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Cottage;

public interface CottageRepository extends JpaRepository<Cottage, Long>{

}
