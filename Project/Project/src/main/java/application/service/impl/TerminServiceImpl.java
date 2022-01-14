package application.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import application.model.Termin;
import application.model.User;
import application.model.Adventure;
import application.model.Reservation;
import application.repository.TerminRepository;
import application.repository.AdventureRepository;
import application.repository.UserRepository;
import application.repository.ReservationRepository;
import application.service.TerminService;

@Service
public class TerminServiceImpl implements TerminService {

	@Autowired
	private TerminRepository terminRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private AdventureRepository adventureRepository;

	@Override
	public Termin findById(Long id) throws AccessDeniedException {
		return terminRepository.findById(id).orElseGet(null);
	}

	@Override
	public List<Termin> findAll() throws AccessDeniedException {
		return terminRepository.findAll();
	}

	@Override
	public Termin save(Termin termin) {
		return terminRepository.save(termin);
	}

	@Override
	public Termin createTermin(Termin termin) {
		Termin termin1 = new Termin();
		termin1.setAdventureTermin(termin.getAdventureTermin());
		termin1.setStart(termin.getStart());
		termin1.setEnd(termin.getEnd());
		termin1.setDuration(termin.getDuration());
		termin1.setReserved(false);
		terminRepository.save(termin1);
		return termin1;
	}

	@Override
	public void updateTermin(Termin termin) {
		Termin termin1 = terminRepository.findById(termin.getId()).orElseGet(null);
		termin1.setAdventureTermin(termin.getAdventureTermin());
		termin1.setStart(termin.getStart());
		termin1.setEnd(termin.getEnd());
		termin1.setDuration(termin.getDuration());
		termin1.setReserved(termin.isReserved());
		termin1.setAction(termin.isAction());
		terminRepository.save(termin1);
	}

	@Override
	public List<Termin> findAllTerminsInstructor(Long instructorId) {
		User user = userRepository.findById(instructorId).orElseGet(null);
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureRepository.findAll();
		List<Termin> adventureTermin = new ArrayList<Termin>();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for (Adventure a : adventures) {
			if (a.getUserAdventure().getId() == user.getId()) {
				adventures1.add(a);
			}
		}
		List<Termin> termins = new ArrayList<Termin>();
		termins = terminRepository.findAll();
		for (Termin t : termins) {
			for (Adventure a : adventures1) {
				if (t.getAdventureTermin().getId() == a.getId()) {
					adventureTermin.add(t);
				}
			}
		}
		return adventureTermin;
	}

	@Override
	public List<Termin> findFreeTerminsInstructor(Long instructorId) {
		User user = userRepository.findById(instructorId).orElseGet(null);
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures = adventureRepository.findAll();
		List<Termin> adventureTermin = new ArrayList<Termin>();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for (Adventure a : adventures) {
			if (a.getUserAdventure().getId() == user.getId()) {
				adventures1.add(a);
			}
		}
		List<Termin> termins = new ArrayList<Termin>();
		termins = terminRepository.findAll();
		for (Termin t : termins) {
			for (Adventure a : adventures1) {
				if (t.getAdventureTermin().getId() == a.getId() && t.isReserved() == false) {
					adventureTermin.add(t);
				}
			}
		}
		return adventureTermin;
	}

	@Override
	public void delete(Long id) {
		Termin termin = terminRepository.findById(id).orElseGet(null);
		terminRepository.delete(termin);
	}

	@Override
	public boolean createReservation(String start, String end, Long adventureId, Long userId) throws ParseException {
		boolean freeAction = false;
		Long actionId = (long) 0;
		User user = userRepository.findById(userId).orElseGet(null);
		Adventure adventure1 = adventureRepository.findById(adventureId).orElseGet(null);
		User instructor = userRepository.findById(adventure1.getUserAdventure().getId()).orElseGet(null);
		boolean free = true;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss");
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		List<Adventure> adventures = new ArrayList<Adventure>();
		adventures1 = adventureRepository.findAll();
		for (Adventure a : adventures1) {
			if (a.getUserAdventure().getId() == instructor.getId()) {
				adventures.add(a);
			}
		}
		List<Termin> usedTermin = new ArrayList<Termin>();
		List<Termin> usedAdventureTermin = new ArrayList<Termin>();
		usedTermin = terminRepository.findAll();
		for (Termin t : usedTermin) {
			for (Adventure a1 : adventures) {
				if (t.getAdventureTermin().getId() == a1.getId()) {
					usedAdventureTermin.add(t);
				}
			}

		}

		Date startDate = dateFormat.parse(start);
		Date endDate = dateFormat.parse(end);

		for (Termin t1 : usedAdventureTermin) {
			Date dmin = dateFormat.parse(t1.getStart());
			Date dmax = dateFormat.parse(t1.getEnd());
			if ((startDate.compareTo(dmin) >= 0 && endDate.compareTo(dmax) <= 0)
					|| (startDate.compareTo(dmin) >= 0 && startDate.compareTo(dmax) <= 0)
					|| (endDate.compareTo(dmin) >= 0 && endDate.compareTo(dmax) <= 0)) {
				if (t1.isAction() == true && t1.isReserved() == false) {
					free = true;
					freeAction = true;
					actionId = t1.getId();
					break;
				} else {
					free = false;
					break;
				}
			} else {
				free = true;
				break;
			}
		}
		if (free == true) {
			List<Reservation> reservations = reservationRepository.findAll();
			for (Reservation r : reservations) {
				if (r.getUserReservation().getId() == userId && r.getAdventureReservation().getId() == adventureId
						&& r.getStart().equals(start)) {
					r.setAdventureReservation(adventure1);
					r.setEnd(end);
					r.setStart(start);
					r.setCreatedReservation(true);
					r.setUserReservation(user);
					reservationRepository.save(r);
				}
			}
			if (freeAction == true) {
				Termin termin1 = terminRepository.findById(actionId).orElseGet(null);
				termin1.setReserved(true);
				terminRepository.save(termin1);
			} else {
				Termin termin1 = new Termin();
				termin1.setAdventureTermin(adventure1);
				termin1.setStart(start);
				termin1.setEnd(end);
				termin1.setDuration(0);
				termin1.setAction(false);
				termin1.setReserved(true);
				terminRepository.save(termin1);
			}
			adventure1.setReserved(true);
			adventureRepository.save(adventure1);
		}

		return free;
	}

	@Override
	public void deleteReservation(Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId).orElseGet(null);
		reservationRepository.delete(reservation);

	}

	@Override
	public List<Reservation> findAllReservationsInstructor(Long instructorId) {
		List<Reservation> reservations1 = new ArrayList<Reservation>();
		List<Reservation> reservations = new ArrayList<Reservation>();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		adventures1 = adventureRepository.findAll();
		List<Adventure> adventures = new ArrayList<Adventure>();
		for (Adventure a : adventures1) {
			if (a.getUserAdventure().getId() == instructorId) {
				adventures.add(a);
			}
		}
		reservations1 = reservationRepository.findAll();
		for (Reservation r : reservations1) {
			for (Adventure a1 : adventures) {
				if (a1.getId() == r.getId()) {
					reservations.add(r);
				}
			}
		}
		return reservations;
	}

	@Override
	public Reservation saveReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	@Override
	public void deleteReservationTermin(Long reservationId, String start, String end) {
		Reservation reservation = reservationRepository.findById(reservationId).orElseGet(null);
		Adventure adventure = adventureRepository.findById(reservation.getAdventureReservation().getId())
				.orElseGet(null);
		Termin termin = new Termin();
		List<Termin> termins = new ArrayList<Termin>();
		termins = terminRepository.findAll();
		for (Termin t : termins) {
			if (t.getAdventureTermin().getId() == adventure.getId() && t.getStart().equals(start)
					&& t.getEnd().equals(end)) {
				termin = t;
				break;
			}
		}
		if(termin.isAction() == true) {
			termin.setReserved(false);
			terminRepository.save(termin);
			
		}
		else {
		terminRepository.delete(termin);
		}
		reservationRepository.delete(reservation);

	}
}
