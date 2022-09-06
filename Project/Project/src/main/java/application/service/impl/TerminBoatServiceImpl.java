package application.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import application.model.Boat;
import application.model.EntitySubscriber;
import application.model.TerminBoat;
import application.model.TerminCottage;
import application.model.User;
import application.repository.TerminBoatRepository;
import application.repository.BoatRepository;
import application.repository.EntitySubscriberRepository;
import application.service.TerminBoatService;
import application.service.UserService;

public class TerminBoatServiceImpl implements TerminBoatService {

	@Autowired
	private TerminBoatRepository terminCottageRepository;
	@Autowired
	private BoatRepository cottageRepository;
	@Autowired
	private Environment env;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private UserService userService;
	@Autowired
	EntitySubscriberRepository subscriberRepository;
	
	@Override
	public boolean createTermin(TerminBoat termin, Long cottageId, int i) throws ParseException {
		TerminBoat term = new TerminBoat();
		boolean create = false;
		Boat cottage = cottageRepository.findById(cottageId).orElseGet(null);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		//ukoliko moze preklop po danima ali ne po satima, koristiti: SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date startDate = dateFormat.parse(termin.getStart());
		Date endDate = dateFormat.parse(termin.getEnd());
		List<TerminBoat> termins = new ArrayList<>();
		termins = finishedReservations(cottageId);
		if(!termins.isEmpty()) {
			for (TerminBoat t1 : termins) {
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date firstDate = sdf.parse(termin.getStart());
        Date secondDate = sdf.parse(termin.getEnd());
        long diff = secondDate.getTime() - firstDate.getTime();
        TimeUnit time = TimeUnit.DAYS;
        long difference = time.convert(diff, TimeUnit.MILLISECONDS);
        
        long diffHours = secondDate.getTime() - firstDate.getTime();
        TimeUnit timeHours = TimeUnit.HOURS;
        long differenceHours = timeHours.convert(diffHours, TimeUnit.MILLISECONDS);
        
        if(differenceHours>24) {
        	if(differenceHours%24 != 0) {
        		difference+=1;
        	}
        } else if (differenceHours < 24 && difference==0) {
        	difference = 1;
        }
        
        
		if(i == 1) {
			if (create) {
				term.setStart(termin.getStart());
				term.setEnd(termin.getEnd());
				term.setDaysDuration(termin.getDaysDuration());
				term.setReserved(false);
				term.setAction(termin.isAction());
				term.setPrice(cottage.getPrice()*difference);
				term.setCapacity(termin.getCapacity());
				term.setCottageTermin(cottage);
				term.setUserReserved(null);
				terminCottageRepository.save(term);
			}
		} else {
			if (create) {
				term.setStart(termin.getStart());
				term.setEnd(termin.getEnd());
				term.setDaysDuration(termin.getDaysDuration());
				term.setReserved(termin.isReserved());
				term.setAction(termin.isAction());
				term.setPrice(cottage.getPrice()*difference);
				term.setCapacity(termin.getCapacity());
				term.setCottageTermin(cottage);
				term.setUserReserved(termin.getUserReserved());
				terminCottageRepository.save(term);
			}
		}
		
		if(term.isAction()) {
			
			String actionEndDate = countActionEndDate(term);
			term.setActionExpireDate(actionEndDate);
			
			List<User> users = new ArrayList<>();
			List<EntitySubscriber> subs = new ArrayList<>();
			
			users = userService.findAll();
			subs = subscriberRepository.findAll();
			
			for(User user : users) {
				for(EntitySubscriber e : subs) {
					if(user.getId().equals(e.getSubscriber().getId()) && e.getCottage().getId().equals(cottage.getId())) {
						SimpleMailMessage mail = new SimpleMailMessage();
						mail.setTo(user.getEmail());
						mail.setFrom(env.getProperty("spring.mail.username"));
						mail.setSubject("AKCIJA");
						mail.setText("Za vikendicu " + cottage.getName() + " imamo sjajnu ponudu za vas!");
						javaMailSender.send(mail);
					}
				}
			}
			
			
		}
		
		//System.out.println("Price: " + term.getPrice());
		return create;
	}

	public boolean reserveTermin(TerminBoat term) {
		
		TerminBoat termin = terminCottageRepository.findById(term.getId()).orElseGet(null);
		boolean reserved = false;
		
		if(termin != null) {
			termin.setReserved(true);
			termin.setUserReserved(term.getUserReserved());
			terminCottageRepository.save(termin);
			reserved = true;
		}

		return reserved;
	}
	
	public boolean cancelReservation(TerminBoat term) {
		
		TerminBoat termin = terminCottageRepository.findById(term.getId()).orElseGet(null);
		boolean canceled = false;
		
		if(termin != null) {
			termin.setReserved(false);
			termin.setUserReserved(null);
			terminCottageRepository.save(termin);
			canceled = true;
		}

		return canceled;
	}
	
	public String countActionEndDate(TerminBoat term) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		String date = sdf.format(new Date());
		Calendar cal = Calendar.getInstance();  
		
        try{  
           cal.setTime(sdf.parse(date));  
        }catch(ParseException e){  
            e.printStackTrace();  
        }  
        
        cal.add(Calendar.DAY_OF_MONTH, term.getDaysDuration());  
        String dateAfter = sdf.format(cal.getTime());
		
		return dateAfter;
	}
	
	@Override
	public TerminBoat save(TerminBoat terminCottage) {
		return terminCottageRepository.save(terminCottage);
	}

	@Override
	public TerminBoat findById(Long id) {
		return terminCottageRepository.findById(id).orElseGet(null);
	}
	
	@Override
	public List<TerminBoat> findAll() {
		return terminCottageRepository.findAll();
	}

	@Override
	public List<TerminBoat> findAllTerminsByCottage(Long cottageId) {
		List<TerminBoat> termins = new ArrayList<>();
		termins = findAll();
		List<TerminBoat> ret = new ArrayList<>();
		for(TerminBoat t : termins) 
			if(t.getCottageTermin().getId() == cottageId)
				ret.add(t);
		
		return ret;
	}
	
	public List<TerminBoat> finishedReservations(Long cottageId) throws ParseException{
		List<TerminBoat> all = new ArrayList<>();
		all = findAllTerminsByCottage(cottageId);
		List<TerminBoat> finished = new ArrayList<>();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat term : all) {

	        Date parsedDate = sdf.parse(term.getEnd());
	        if(date.compareTo(parsedDate)>0 && term.getUserReserved()!=null) {
	        	finished.add(term);
	        }
		}
		
		return finished;
	}

	@Override
	public boolean updateTermin(TerminBoat terminCottage) throws ParseException {
		TerminBoat termin = terminCottageRepository.findById(terminCottage.getId()).orElseGet(null);
		boolean edit = false;
		
		if(termin!=null) {
			Long cottageId = terminCottage.getCottageTermin().getId();
			List<TerminBoat> termins = findAllTerminsByCottage(cottageId);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			//ukoliko moze preklop po danima ali ne po satima, koristiti: SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date startDate = dateFormat.parse(termin.getStart());
			Date endDate = dateFormat.parse(termin.getEnd());
			if(!termins.isEmpty()) {
				for (TerminBoat t1 : termins) {
					Date dmin = dateFormat.parse(t1.getStart());
					Date dmax = dateFormat.parse(t1.getEnd());
					if (((startDate.compareTo(dmin) <= 0 && endDate.compareTo(dmax) >= 0)
							|| (startDate.compareTo(dmin) >= 0 && endDate.compareTo(dmax) <= 0)
							|| (startDate.compareTo(dmin) <= 0 && endDate.compareTo(dmax) <= 0 && endDate.compareTo(dmin) >= 0) 
							|| (startDate.compareTo(dmin) >= 0 && startDate.compareTo(dmax) <= 0 && endDate.compareTo(dmax) >= 0)) && t1.getId() != terminCottage.getId()) {
						edit = false;
						break;
					} else {
						edit = true;
					}
				}
			} else {
				edit = true;
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	        Date firstDate = sdf.parse(termin.getStart());
	        Date secondDate = sdf.parse(termin.getEnd());
	        long diff = secondDate.getTime() - firstDate.getTime();
	        TimeUnit time = TimeUnit.DAYS;
	        long difference = time.convert(diff, TimeUnit.MILLISECONDS);
	        
	        long diffHours = secondDate.getTime() - firstDate.getTime();
	        TimeUnit timeHours = TimeUnit.HOURS;
	        long differenceHours = timeHours.convert(diffHours, TimeUnit.MILLISECONDS);
	        
	        if(differenceHours>24) {
	        	if(differenceHours%24 != 0) {
	        		difference+=1;
	        	}
	        } else if (differenceHours < 24 && difference==0) {
	        	difference = 1;
	        }
			
	        if(edit) {
	        	if(termin.isAction() && !terminCottage.isAction()) 
					termin.setActionExpireDate("expired");
				
				if(!termin.isAction() && terminCottage.isAction()) 
					termin.setActionExpireDate(countActionEndDate(terminCottage));
				
				if(termin.isAction() && terminCottage.isAction() && !(termin.getDaysDuration()==terminCottage.getDaysDuration())) 
					termin.setActionExpireDate(countActionEndDate(terminCottage));
				
				termin.setAction(terminCottage.isAction());
				termin.setCapacity(terminCottage.getCapacity());
				termin.setCottageTermin(terminCottage.getCottageTermin());
				termin.setDaysDuration(terminCottage.getDaysDuration());
				termin.setEnd(terminCottage.getEnd());
				termin.setPrice(terminCottage.getPrice()*difference);
				termin.setReserved(terminCottage.isReserved());
				termin.setStart(terminCottage.getStart());
				termin.setUserReserved(terminCottage.getUserReserved());
				save(termin);
	        }
			
		}
		
		return edit;
		
	}

	@Override
	public void deleteTermin(Long id) {
		TerminBoat terminToDelete = terminCottageRepository.findById(id).orElseGet(null);
		terminCottageRepository.delete(terminToDelete);
		
	}
}
