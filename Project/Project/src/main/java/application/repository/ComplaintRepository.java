package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

}
