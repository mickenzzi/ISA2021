package application.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Cottage;
import application.model.ReservationCottage;
import application.model.TerminCottage;
import application.repository.CottageRepository;
import application.repository.TerminCottageRepository;
import application.service.TerminCottageService;

@Service
public class TerminCottageServiceImpl implements TerminCottageService {

	@Autowired
	TerminCottageRepository terminCottageRepository;
	
	@Autowired
	CottageRepository cottageRepository;
	
	@Override
	public boolean createTermin(TerminCottage termin, Long cottageId) throws ParseException {
		TerminCottage term = new TerminCottage();
		boolean create = false;
		Cottage cottage = cottageRepository.findById(cottageId).orElseGet(null);
		List<TerminCottage> termins = findAllTerminsByCottage(cottageId);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date startDate = dateFormat.parse(termin.getStart());
		Date endDate = dateFormat.parse(termin.getEnd());
		if(!termins.isEmpty()) {
			for (TerminCottage t1 : termins) {
				Date dmin = dateFormat.parse(t1.getStart());
				Date dmax = dateFormat.parse(t1.getEnd());
				if ((startDate.compareTo(dmin) <= 0 && endDate.compareTo(dmax) >= 0)
						|| (startDate.compareTo(dmin) >= 0 && endDate.compareTo(dmax) <= 0)
						|| (startDate.compareTo(dmin) <= 0 && endDate.compareTo(dmax) <= 0 && endDate.compareTo(dmin) >= 0) 
						|| (startDate.compareTo(dmin) >= 0 && startDate.compareTo(dmax) <= 0 && endDate.compareTo(dmax) >= 0)) {
					create = false;
					break;
				} else {
					create = true;
				}
			}
		} else {
			create = true;
		}
		//ubaciti parsiranje neko jer ne racuna dobro
		LocalDate start = LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDay());
	    LocalDate end = LocalDate.of(endDate.getYear(), endDate.getMonth(), endDate.getDay());

	    Period period = Period.between(start, end);
	    int diff = Math.abs(period.getDays());
		System.out.println(diff);
		if (create) {
			term.setStart(termin.getStart());
			term.setEnd(termin.getEnd());
			term.setDaysDuration(termin.getDaysDuration());
			term.setReserved(false);
			term.setAction(termin.isAction());
			term.setPrice(cottage.getPrice()*diff);
			term.setCapacity(termin.getCapacity());
			term.setCottageTermin(cottage);
			terminCottageRepository.save(term);
		}
		System.out.println("Days between: " + term.getPrice());
		return create;
	}

	@Override
	public TerminCottage save(TerminCottage terminCottage) {
		return terminCottageRepository.save(terminCottage);
	}

	@Override
	public TerminCottage findById(Long id) {
		return terminCottageRepository.findById(id).orElseGet(null);
	}

	@Override
	public List<TerminCottage> findAll() {
		return terminCottageRepository.findAll();
	}

	@Override
	public List<TerminCottage> findAllTerminsByCottage(Long cottageId) {
		List<TerminCottage> termins = findAll();
		List<TerminCottage> ret = new ArrayList<>();
		for(TerminCottage t : termins) 
			if(t.getCottageTermin().getId() == cottageId)
				ret.add(t);
		
		return ret;
	}

	@Override
	public void updateTermin(TerminCottage terminCottage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTermin(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean createReservation(String startDate, String endDate, Long adventureId, Long userId)
			throws ParseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReservationCottage saveReservation(ReservationCottage reservation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReservationCottage findReservationById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteReservation(Long reservationId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteReservationTermin(Long reservationId, String start, String end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ReservationCottage> findAllReservationsByCottage(Long cottageId) {
		// TODO Auto-generated method stub
		return null;
	}

}
