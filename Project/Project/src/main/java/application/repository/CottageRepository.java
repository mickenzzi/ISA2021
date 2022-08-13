package application.repository;

import application.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageRepository extends JpaRepository<Cottage, Long>{
}
