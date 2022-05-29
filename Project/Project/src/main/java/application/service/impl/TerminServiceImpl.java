package application.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import application.model.Termin;
import application.model.User;
import application.model.Adventure;
import application.model.Loyalty;
import application.model.Reservation;
import application.repository.TerminRepository;
import application.repository.AdventureRepository;
import application.repository.LoyaltyRepository;
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
	@Autowired
	private LoyaltyRepository loyaltyRepository;
	
	Comparator<Reservation> compareByReserved = new Comparator<Reservation>() {
		@Override
		public int compare(Reservation r1, Reservation r2) {
			return Boolean.compare(r1.isCreatedReservation(), r2.isCreatedReservation());
		}
	};


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
	public boolean createTermin(Termin termin, Long instructorId) throws ParseException {
		Termin termin1 = new Termin();
		boolean create = false;
		User instructor = userRepository.findById(instructorId).orElseGet(null);
		List<Adventure> adventures = adventureRepository.findAll();
		List<Adventure> adventures1 = new ArrayList<Adventure>();
		for (Adventure a : adventures) {
			if (a.getUserAdventure().getId() == instructorId) {
				adventures1.add(a);
			}
		}
		List<Termin> termins = new ArrayList<Termin>();
		List<Termin> termins1 = new ArrayList<Termin>();
		termins = terminRepository.findAll();
		for (Termin t : termins) {
			if (t.getInstructorTermin().getId() == instructorId) {
				termins1.add(t);
			}
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss");
		Date startDate = dateFormat.parse(termin.getStart());
		Date endDate = dateFormat.parse(termin.getEnd());
		for (Termin t1 : termins1) {
			Date dmin = dateFormat.parse(t1.getStart());
			Date dmax = dateFormat.parse(t1.getEnd());
			if ((startDate.compareTo(dmin) >= 0 && endDate.compareTo(dmax) <= 0)
					|| (startDate.compareTo(dmin) >= 0 && startDate.compareTo(dmax) <= 0)
					|| (endDate.compareTo(dmin) >= 0 && endDate.compareTo(dmax) <= 0)) {
				create = false;
				break;
			} else {
				create = true;
			}

		}
		if (create) {
			termin1.setInstructorTermin(instructor);
			termin1.setStart(termin.getStart());
			termin1.setEnd(termin.getEnd());
			termin1.setDuration(termin.getDuration());
			termin1.setReserved(false);
			termin1.setAction(false);
			termin1.setAdventureTermin(adventures1.get(0));
			terminRepository.save(termin1);
		}
		return create;
	}

	@Override
	public void updateTermin(Termin termin) {
		Termin termin1 = terminRepository.findById(termin.getId()).orElseGet(null);
		termin1.setStart(termin.getStart());
		termin1.setEnd(termin.getEnd());
		termin1.setDuration(termin.getDuration());
		terminRepository.save(termin1);
	}

	@Override
	public List<Termin> findAllTerminsInstructor(Long instructorId) {
		List<Termin> termins = new ArrayList<Termin>();
		List<Termin> instructorTermins = new ArrayList<Termin>();
		termins = terminRepository.findAll();
		for (Termin t : termins) {
			if (t.getInstructorTermin().getId() == instructorId) {
				instructorTermins.add(t);
			}
		}
		
		return instructorTermins;
	}

	@Override
	public List<Termin> findFreeTerminsInstructor(Long instructorId) {
		List<Termin> instructorTermins = new ArrayList<Termin>();
		List<Termin> termins = new ArrayList<Termin>();
		termins = terminRepository.findAll();
		for (Termin t : termins) {
			if (t.getInstructorTermin().getId() == instructorId) {
				instructorTermins.add(t);
			}
		}
		return instructorTermins;
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
		List<Termin> usedTermin = new ArrayList<Termin>();
		List<Termin> usedInstructorTermins = new ArrayList<Termin>();
		usedTermin = terminRepository.findAll();
		for (Termin t : usedTermin) {
			if (t.getInstructorTermin().getId() == instructor.getId()) {
				usedInstructorTermins.add(t);
			}
		}

		Date startDate = dateFormat.parse(start);
		Date endDate = dateFormat.parse(end);

		for (Termin t1 : usedInstructorTermins) {
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
					r.setPrice(adventure1.getPriceList());
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
				termin1.setCapacity((long) adventure1.getMaxNumber());
				termin1.setInstructorTermin(instructor);
				terminRepository.save(termin1);
			}
			adventure1.setReserved(true);
			adventureRepository.save(adventure1);
			user.setCollectedPoints(user.getCollectedPoints()+1);
			Loyalty bronze = loyaltyRepository.findByName("BRONZE");
			Loyalty silver = loyaltyRepository.findByName("SILVER");
			Loyalty gold = loyaltyRepository.findByName("GOLD");
			if(user.getCollectedPoints() == silver.getPoints()) {
				user.setLoyaltyStatus(silver.getName());
			}
			else if(user.getCollectedPoints() == gold.getPoints()) {
				user.setLoyaltyStatus(gold.getName());
			}
			else {
				user.setLoyaltyStatus(bronze.getName());
			}
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
				if (a1.getId() == r.getAdventureReservation().getId()) {
					reservations.add(r);
				}
			}
		}
		Collections.sort(reservations, compareByReserved);
		return reservations;
	}

	@Override
	public Reservation saveReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}
	
	@Override
	public Reservation findReservationById(Long id) throws AccessDeniedException {
		return reservationRepository.findById(id).orElseGet(null);
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
		if (termin.isAction() == true) {
			termin.setReserved(false);
			terminRepository.save(termin);

		} else {
			terminRepository.delete(termin);
		}
		reservationRepository.delete(reservation);

	}
}
