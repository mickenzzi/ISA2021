package application.service;

import java.text.ParseException;
import java.util.List;

public interface CottageBusinessService {

	Double getOwnerProfit(String start, String end, Long id) throws ParseException;

	List<Integer> numberOfReservationsOnMonth(Long id);

	List<Integer> numberOfReservedDaysPerMonth(Long id);

	List<Integer> numberOfReservationsOnWeek(Long id);

	List<Integer> numberOfReservedDaysPerWeek(Long id) throws ParseException;

	List<Integer> numberOfReservationsOnDay(Long id) throws ParseException;

	List<Integer> numberOfReservedDays(Long id) throws ParseException;

	List<Double> profitPerMonth(Long id) throws ParseException;

	List<Double> profitPerWeek(Long id) throws ParseException;

	List<Double> profitPerDay(Long id) throws ParseException;
}
