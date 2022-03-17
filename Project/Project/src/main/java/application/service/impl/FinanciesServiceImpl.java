package application.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Financies;
import application.model.Reservation;
import application.repository.FinanciesRepository;
import application.repository.ReservationRepository;
import application.service.FinanciesService;
import java.util.Collections;

@Service
public class FinanciesServiceImpl implements FinanciesService {
	@Autowired
	private FinanciesRepository financiesRepository;
	@Autowired
	private ReservationRepository reservationRepository;

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
			percent = nearPercent(r1.getEnd());
			profit += (r1.getPrice() * percent) / 100;
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
			percent = nearPercent(rJan.getEnd());
			profit += rJan.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rFeb : usableFebReservations) {
			percent = nearPercent(rFeb.getEnd());
			profit += rFeb.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rMar : usableMarReservations) {
			percent = nearPercent(rMar.getEnd());
			profit += rMar.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rApr : usableAprReservations) {
			percent = nearPercent(rApr.getEnd());
			profit += rApr.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rMay : usableMayReservations) {
			percent = nearPercent(rMay.getEnd());
			profit += rMay.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rJun : usableJunReservations) {
			percent = nearPercent(rJun.getEnd());
			profit += rJun.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rJul : usableJulReservations) {
			percent = nearPercent(rJul.getEnd());
			profit += rJul.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rAug : usableAugReservations) {
			percent = nearPercent(rAug.getEnd());
			profit += rAug.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rSep : usableSepReservations) {
			percent = nearPercent(rSep.getEnd());
			profit += rSep.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rOct : usableOctReservations) {
			percent = nearPercent(rOct.getEnd());
			profit += rOct.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rNov : usableNovReservations) {
			percent = nearPercent(rNov.getEnd());
			profit += rNov.getPrice() * (percent / 100);
		}
		profits.add(profit);
		profit = 0;
		for (Reservation rDec : usableDecReservations) {
			percent = nearPercent(rDec.getEnd());
			profit += rDec.getPrice() * (percent / 100);
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
}
