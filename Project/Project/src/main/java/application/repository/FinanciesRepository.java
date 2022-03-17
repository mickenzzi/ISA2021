package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Financies;

public interface FinanciesRepository extends JpaRepository<Financies, Long> {

}
