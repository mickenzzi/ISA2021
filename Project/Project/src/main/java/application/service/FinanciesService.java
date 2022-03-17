package application.service;

import java.text.ParseException;
import java.util.List;

public interface FinanciesService {
	Double getYearProfit(String year) throws ParseException;
	List<Double> getMonthProfit(String year) throws ParseException;
	Double getPercent();
	void editPercent(double percent);
}
