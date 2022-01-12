package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}
