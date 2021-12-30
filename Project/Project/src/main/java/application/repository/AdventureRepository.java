package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Adventure;

public interface AdventureRepository extends JpaRepository<Adventure, Long>{

}
