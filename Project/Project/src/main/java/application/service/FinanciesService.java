package application.service;

import java.text.ParseException;
import java.util.List;

public interface FinanciesService {
	Double getYearProfit(String year) throws ParseException;
	List<Double> getMonthProfit(String year) throws ParseException;
	List<Double> getReservationsPerMonth(Long id) throws ParseException;
	List<Double> getReservationsPerWeek(Long id) throws ParseException;
	List<Double> getReservationsPerDay(Long id) throws ParseException;
	Double getPercent();
	void editPercent(double percent);
	Double getInstructorProfit(Long id, String start, String end) throws ParseException;
}
