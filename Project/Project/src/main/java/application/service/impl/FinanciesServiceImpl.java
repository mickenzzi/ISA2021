package application.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Adventure;
import application.model.Financies;
import application.model.Loyalty;
import application.model.Reservation;
import application.model.User;
import application.repository.AdventureRepository;
import application.repository.FinanciesRepository;
import application.repository.LoyaltyRepository;
import application.repository.ReservationRepository;
import application.repository.UserRepository;
import application.service.FinanciesService;
import java.util.Collections;

@Service
public class FinanciesServiceImpl implements FinanciesService {
	@Autowired
	private FinanciesRepository financiesRepository;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private LoyaltyRepository loyaltyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdventureRepository adventureRepository;


	@Override
	public Double getYearProfit(String year) throws ParseException {
		double profit = 0;
		double percent = 0;
		List<Reservation> reservations = reservationRepository.findAll();
		List<Reservation> usableReservations = new ArrayList<Reservation>();
		for (Reservation r : reservations) {
			if (r.getEnd().contains(year)) {
				usableReservations.add(r);
			}
		}
		for (Reservation r1 : usableReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(r1.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(r1.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = r1.getPrice() - loyalty.getDiscount()*r1.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		return profit;
	}

	@Override
	public List<Double> getMonthProfit(String year) throws ParseException {
		List<Double> profits = new ArrayList<Double>();
		double profit = 0;
		double percent = 0;
		List<Reservation> reservations = reservationRepository.findAll();
		List<Reservation> usableReservations = new ArrayList<Reservation>();
		List<Reservation> usableJanReservations = new ArrayList<Reservation>();
		List<Reservation> usableFebReservations = new ArrayList<Reservation>();
		List<Reservation> usableMarReservations = new ArrayList<Reservation>();
		List<Reservation> usableAprReservations = new ArrayList<Reservation>();
		List<Reservation> usableMayReservations = new ArrayList<Reservation>();
		List<Reservation> usableJunReservations = new ArrayList<Reservation>();
		List<Reservation> usableJulReservations = new ArrayList<Reservation>();
		List<Reservation> usableAugReservations = new ArrayList<Reservation>();
		List<Reservation> usableSepReservations = new ArrayList<Reservation>();
		List<Reservation> usableOctReservations = new ArrayList<Reservation>();
		List<Reservation> usableNovReservations = new ArrayList<Reservation>();
		List<Reservation> usableDecReservations = new ArrayList<Reservation>();
		for (Reservation r : reservations) {
			if (r.getEnd().contains(year)) {
				usableReservations.add(r);
			}
		}
		for (Reservation r1 : usableReservations) {
			if (r1.getEnd().contains("Jan")) {
				usableJanReservations.add(r1);
			} else if (r1.getEnd().contains("Feb")) {
				usableFebReservations.add(r1);
			} else if (r1.getEnd().contains("Mar")) {
				usableMarReservations.add(r1);
			} else if (r1.getEnd().contains("Apr")) {
				usableAprReservations.add(r1);
			} else if (r1.getEnd().contains("May")) {
				usableMayReservations.add(r1);
			} else if (r1.getEnd().contains("Jun")) {
				usableJunReservations.add(r1);
			} else if (r1.getEnd().contains("Jul")) {
				usableJulReservations.add(r1);
			} else if (r1.getEnd().contains("Aug")) {
				usableAugReservations.add(r1);
			} else if (r1.getEnd().contains("Sep")) {
				usableSepReservations.add(r1);
			} else if (r1.getEnd().contains("Oct")) {
				usableOctReservations.add(r1);
			} else if (r1.getEnd().contains("Nov")) {
				usableNovReservations.add(r1);
			} else if (r1.getEnd().contains("Dec")) {
				usableDecReservations.add(r1);
			}
		}
		for (Reservation rJan : usableJanReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rJan.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rJan.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rJan.getPrice() - loyalty.getDiscount()*rJan.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rFeb : usableFebReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rFeb.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rFeb.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rFeb.getPrice() - loyalty.getDiscount()*rFeb.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rMar : usableMarReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rMar.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rMar.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rMar.getPrice() - loyalty.getDiscount()*rMar.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rApr : usableAprReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rApr.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rApr.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rApr.getPrice() - loyalty.getDiscount()*rApr.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rMay : usableMayReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rMay.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rMay.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rMay.getPrice() - loyalty.getDiscount()*rMay.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rJun : usableJunReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rJun.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rJun.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rJun.getPrice() - loyalty.getDiscount()*rJun.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rJul : usableJulReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rJul.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rJul.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rJul.getPrice() - loyalty.getDiscount()*rJul.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rAug : usableAugReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rAug.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rAug.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rAug.getPrice() - loyalty.getDiscount()*rAug.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rSep : usableSepReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rSep.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rSep.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rSep.getPrice() - loyalty.getDiscount()*rSep.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rOct : usableOctReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rOct.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rOct.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rOct.getPrice() - loyalty.getDiscount()*rOct.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rNov : usableNovReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rNov.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rNov.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rNov.getPrice() - loyalty.getDiscount()*rNov.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rDec : usableDecReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(rDec.getUserReservation().getLoyaltyStatus());
			percent = nearPercent(rDec.getEnd());
			double oldPrice = 0;
			double newPrice = 0;
			oldPrice = rDec.getPrice() - loyalty.getDiscount()*rDec.getPrice()/100;
			newPrice = percent*oldPrice/100;
			profit += newPrice;
		}
		profits.add(profit);
		profit = 0;
		return profits;
	}

	public double nearPercent(String reservationEnd) throws ParseException {
		double percent = 0;
		long min = 0;
		HashMap<Long, Double> map = new HashMap<Long, Double>();
		List<Long> percents = new ArrayList<Long>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss");
		Date resEnd = dateFormat.parse(reservationEnd);
		List<Financies> financies = financiesRepository.findAll();
		if (!financies.isEmpty()) {
			for (Financies f : financies) {
				Date finDefine = dateFormat.parse(f.getDefine());
				if (resEnd.compareTo(finDefine) >= 0) {
					min = resEnd.getTime() - finDefine.getTime();
					map.put(min, f.getPercent());
					percents.add(min);
				}
			}

			min = Collections.min(percents);
			percent = map.get(min);
		} else {
			percent = 1;
		}
		return percent;
	}

	@Override
	public Double getPercent() {
		double percent = 0;
		List<Financies> financies = financiesRepository.findAll();
		percent = financies.get(financies.size() - 1).getPercent();
		return percent;
	}

	@Override
	public void editPercent(double percent) {
		List<Financies> allFinancies = financiesRepository.findAll();
		for (Financies f : allFinancies) {
			if (f.getPercent() == percent) {
				allFinancies.remove(f);
				break;
			}
		}
		Financies financies = new Financies();
		financies.setPercent(percent);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		financies.setDefine(date1);
		financiesRepository.save(financies);
	}

	@Override
	public Double getInstructorProfit(Long id, String start, String end) throws ParseException {
		double profit = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy");
		Date min = dateFormat.parse(start);
		Date max = dateFormat.parse(end);
		List<Reservation> reservations = reservationRepository.findAll();
		List<Reservation> usableReservations = new ArrayList<Reservation>();
		for(Reservation r: reservations) {
			Date startDate = dateFormat.parse(r.getStart());
			Date endDate = dateFormat.parse(r.getEnd());
			if(r.isCreatedReservation()== true && startDate.compareTo(min) >= 0 && endDate.compareTo(max) <= 0 && r.getAdventureReservation().getUserAdventure().getId() == id) {
				usableReservations.add(r);
			}
		}
		for(Reservation r1: usableReservations) {
			Loyalty loyalty = loyaltyRepository.findByName(r1.getUserReservation().getLoyaltyStatus());
			Loyalty loyalty1 = loyaltyRepository.findByName(r1.getAdventureReservation().getUserAdventure().getLoyaltyStatus());
			double newPrice = 0;
			double instructorProfit = 0;
			newPrice = r1.getPrice() - loyalty.getDiscount()*r1.getPrice()/100;
			instructorProfit = newPrice*loyalty1.getDiscount()/100;
			profit = profit + instructorProfit;
		}
		return profit;
	}

	@Override
	public List<Double> getReservationsPerMonth(Long id) throws ParseException {
		List<Double> resNumber = new ArrayList<Double>();
		User instructor = userRepository.findById(id).orElseGet(null);
		List<Adventure> adventures = adventureRepository.findAll();
		List<Adventure> instructorAdventures = new ArrayList<Adventure>();
		for(Adventure a: adventures) {
			if(a.getUserAdventure().getId() == instructor.getId()) {
				instructorAdventures.add(a);
			}
		}
		List<Reservation> reservations = reservationRepository.findAll();
		List<Reservation> usableReservations = new ArrayList<Reservation>();
		List<Reservation> usableJanReservations = new ArrayList<Reservation>();
		List<Reservation> usableFebReservations = new ArrayList<Reservation>();
		List<Reservation> usableMarReservations = new ArrayList<Reservation>();
		List<Reservation> usableAprReservations = new ArrayList<Reservation>();
		List<Reservation> usableMayReservations = new ArrayList<Reservation>();
		List<Reservation> usableJunReservations = new ArrayList<Reservation>();
		List<Reservation> usableJulReservations = new ArrayList<Reservation>();
		List<Reservation> usableAugReservations = new ArrayList<Reservation>();
		List<Reservation> usableSepReservations = new ArrayList<Reservation>();
		List<Reservation> usableOctReservations = new ArrayList<Reservation>();
		List<Reservation> usableNovReservations = new ArrayList<Reservation>();
		List<Reservation> usableDecReservations = new ArrayList<Reservation>();
		for(Adventure a: instructorAdventures) {
			for(Reservation r: reservations) {
				if(r.getAdventureReservation().getId() == a.getId())
				usableReservations.add(r);
			}
		}
		for(Reservation r: usableReservations) {
			if (r.getStart().contains("Jan")) {
				usableJanReservations.add(r);
			} else if (r.getStart().contains("Feb")) {
				usableFebReservations.add(r);
			} else if (r.getStart().contains("Mar")) {
				usableMarReservations.add(r);
			} else if (r.getStart().contains("Apr")) {
				usableAprReservations.add(r);
			} else if (r.getStart().contains("May")) {
				usableMayReservations.add(r);
			} else if (r.getStart().contains("Jun")) {
				usableJunReservations.add(r);
			} else if (r.getStart().contains("Jul")) {
				usableJulReservations.add(r);
			} else if (r.getStart().contains("Aug")) {
				usableAugReservations.add(r);
			} else if (r.getStart().contains("Sep")) {
				usableSepReservations.add(r);
			} else if (r.getStart().contains("Oct")) {
				usableOctReservations.add(r);
			} else if (r.getStart().contains("Nov")) {
				usableNovReservations.add(r);
			} else if (r.getStart().contains("Dec")) {
				usableDecReservations.add(r);
			}
		}
		resNumber.add((double) usableJanReservations.size());
		resNumber.add((double) usableFebReservations.size());
		resNumber.add((double) usableMarReservations.size());
		resNumber.add((double) usableAprReservations.size());
		resNumber.add((double) usableMayReservations.size());
		resNumber.add((double) usableJunReservations.size());
		resNumber.add((double) usableJulReservations.size());
		resNumber.add((double) usableAugReservations.size());
		resNumber.add((double) usableSepReservations.size());
		resNumber.add((double) usableOctReservations.size());
		resNumber.add((double) usableNovReservations.size());
		resNumber.add((double) usableDecReservations.size());
		return resNumber;
	}

	@Override
	public List<Double> getReservationsPerWeek(Long id) throws ParseException {
		List<Double> resNumber = new ArrayList<Double>();
		User instructor = userRepository.findById(id).orElseGet(null);
		List<Adventure> adventures = adventureRepository.findAll();
		List<Adventure> instructorAdventures = new ArrayList<Adventure>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy");
		for(Adventure a: adventures) {
			if(a.getUserAdventure().getId() == instructor.getId()) {
				instructorAdventures.add(a);
			}
		}
		List<Reservation> reservations = reservationRepository.findAll();
		List<Reservation> usableReservations = new ArrayList<Reservation>();
		List<Reservation> usableFirstWeekReservations = new ArrayList<Reservation>();
		List<Reservation> usableSecondWeekReservations = new ArrayList<Reservation>();
		List<Reservation> usableThirdWeekReservations = new ArrayList<Reservation>();
		List<Reservation> usableFourthWeekReservations = new ArrayList<Reservation>();
		List<Reservation> usableFifthWeekReservations = new ArrayList<Reservation>();
		for(Adventure a: instructorAdventures) {
			for(Reservation r: reservations) {
				if(r.getAdventureReservation().getId() == a.getId())
				usableReservations.add(r);
			}
		}
		for(Reservation r: usableReservations) {
			Date start = dateFormat.parse(r.getStart());
			if (start.getDate() <= 7) {
				usableFirstWeekReservations.add(r);
			} else if (start.getDate() <= 14) {
				usableSecondWeekReservations.add(r);
			} else if (start.getDate() <= 21) {
				usableThirdWeekReservations.add(r);
			} else if (start.getDate() <= 28) {
				usableFourthWeekReservations.add(r);
			} else if (start.getDate() <= 31) {
				usableFifthWeekReservations.add(r);
			}
		}
		resNumber.add((double) usableFirstWeekReservations.size());
		resNumber.add((double) usableSecondWeekReservations.size());
		resNumber.add((double) usableThirdWeekReservations.size());
		resNumber.add((double) usableFourthWeekReservations.size());
		resNumber.add((double) usableFifthWeekReservations.size());
		return resNumber;
	}

	@Override
	public List<Double> getReservationsPerDay(Long id) throws ParseException {
		List<Double> resNumber = new ArrayList<Double>();
		User instructor = userRepository.findById(id).orElseGet(null);
		List<Adventure> adventures = adventureRepository.findAll();
		List<Adventure> instructorAdventures = new ArrayList<Adventure>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyy");
		for(Adventure a: adventures) {
			if(a.getUserAdventure().getId() == instructor.getId()) {
				instructorAdventures.add(a);
			}
		}
		List<Reservation> reservations = reservationRepository.findAll();
		List<Reservation> usableReservations = new ArrayList<Reservation>();
		List<Reservation> usableMondayReservations = new ArrayList<Reservation>();
		List<Reservation> usableTuesdayReservations = new ArrayList<Reservation>();
		List<Reservation> usableWednesdayReservations = new ArrayList<Reservation>();
		List<Reservation> usableThursdayReservations = new ArrayList<Reservation>();
		List<Reservation> usableFridayReservations = new ArrayList<Reservation>();
		List<Reservation> usableSaturdayReservations = new ArrayList<Reservation>();
		List<Reservation> usableSundayReservations = new ArrayList<Reservation>();
		for(Adventure a: instructorAdventures) {
			for(Reservation r: reservations) {
				if(r.getAdventureReservation().getId() == a.getId())
				usableReservations.add(r);
			}
		}
		for(Reservation r: usableReservations) {
			Date start = dateFormat.parse(r.getStart());
			if (start.getDay() == 0) {
				usableSundayReservations.add(r);
			} else if (start.getDay() == 1) {
				usableMondayReservations.add(r);
			} else if (start.getDay() == 2) {
				usableTuesdayReservations.add(r);
			} else if (start.getDay() == 3) {
				usableThursdayReservations.add(r);
			} else if (start.getDay() == 4) {
				usableFridayReservations.add(r);
			} else if (start.getDay() == 5) {
				usableFridayReservations.add(r);
			} else if (start.getDay() == 6) {
				usableFridayReservations.add(r);
			}
		}
		resNumber.add((double) usableMondayReservations.size());
		resNumber.add((double) usableTuesdayReservations.size());
		resNumber.add((double) usableWednesdayReservations.size());
		resNumber.add((double) usableThursdayReservations.size());
		resNumber.add((double) usableFridayReservations.size());
		resNumber.add((double) usableSaturdayReservations.size());
		resNumber.add((double) usableSundayReservations.size());
		return resNumber;
	}
}
