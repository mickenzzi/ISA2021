package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Termin;

public interface TerminRepository extends JpaRepository<Termin, Long>{

}
