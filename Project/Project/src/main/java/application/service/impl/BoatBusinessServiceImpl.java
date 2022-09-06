package application.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.Boat;
import application.model.TerminBoat;
import application.service.BoatBusinessService;
import application.service.BoatService;
import application.service.TerminBoatService;

@Service
public class BoatBusinessServiceImpl implements BoatBusinessService{
	@Autowired
	private BoatService cottageService;
	@Autowired
	private TerminBoatService terminService;
	
	@Override
	public Double getOwnerProfit(String startDate, String endDate, Long id) throws ParseException {
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		List<Boat> cottages = cottageService.findOwnerCottages(id);
		
		for(Boat c : cottages) {
			List<TerminBoat> terminsTemp = new ArrayList<>();
			terminsTemp = terminService.finishedReservations(c.getId());
			for(TerminBoat t : terminsTemp) {
				cottageTermins.add(t);
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        double profit = 0;
        
		for(TerminBoat t : cottageTermins) {
			//System.out.println("nasao jednu");
			Date tStart = sdf.parse(t.getStart());
			Date tEnd = sdf.parse(t.getEnd());
			//System.out.println("start: " + start + "end: " + end);
			//System.out.println("termin start: " + tStart + "termin end: " + tEnd);
			if(tStart.compareTo(start) >= 0 && tEnd.compareTo(end) <= 0) {
				profit += t.getPrice();
			}
			
		}
		
		return profit;
	}
	
	@Override
	public List<Integer> numberOfReservationsOnMonth(Long id) throws ParseException{
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		List<Boat> cottages = cottageService.findOwnerCottages(id);
		for(Boat c : cottages) {
			List<TerminBoat> terminsTemp = new ArrayList<>();
			terminsTemp = terminService.finishedReservations(c.getId());
			for(TerminBoat t : terminsTemp) {
				cottageTermins.add(t);
			}
		}
		int jan = 0;
		int feb = 0;
		int mar = 0;
		int apr = 0;
		int may = 0;
		int jun = 0;
		int jul = 0;
		int aug = 0;
		int sep = 0;
		int oct = 0;
		int nov = 0;
		int dec = 0;
		for(TerminBoat t: cottageTermins) {
			
			if(t.getStart().contains("Jan")) {
				jan++;
				if(t.getEnd().contains("Feb"))
					feb++;
			} else if (t.getStart().contains("Feb")){
				feb++;
				if(t.getEnd().contains("Mar"))
					mar++;
			} else if (t.getStart().contains("Mar")){
				mar++;
				if(t.getEnd().contains("Apr"))
					apr++;
			} else if (t.getStart().contains("Apr")){
				apr++;
				if(t.getEnd().contains("May"))
					may++;
			} else if (t.getStart().contains("May")){
				may++;
				if(t.getEnd().contains("Jun"))
					jun++;
			} else if (t.getStart().contains("Jul")){
				jun++;
				if(t.getEnd().contains("Jul"))
					jul++;
			} else if (t.getStart().contains("Jul")){
				jul++;
				if(t.getEnd().contains("Aug"))
					aug++;
			} else if (t.getStart().contains("Aug")){
				aug++;
				if(t.getEnd().contains("Sep"))
					sep++;
			} else if (t.getStart().contains("Sep")){
				sep++;
				if(t.getEnd().contains("Oct"))
					oct++;
			} else if (t.getStart().contains("Oct")){
				oct++;
				if(t.getEnd().contains("Nov"))
					nov++;
			} else if (t.getStart().contains("Nov")){
				nov++;
				if(t.getEnd().contains("Dec"))
					dec++;
			} else if (t.getStart().contains("Dec")){
				dec++;
				if(t.getEnd().contains("Jan"))
					jan++;
			}
		}
		
		List<Integer> ret = new ArrayList<>();
		ret.add(jan);
		ret.add(feb);
		ret.add(mar);
		ret.add(apr);
		ret.add(may);
		ret.add(jun);
		ret.add(jul);
		ret.add(aug);
		ret.add(sep);
		ret.add(oct);
		ret.add(nov);
		ret.add(dec);
		
		return ret;
	}
	
	@Override
	public List<Integer> numberOfReservedDaysPerMonth(Long id) throws ParseException{
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		List<Boat> cottages = cottageService.findOwnerCottages(id);
		for(Boat c : cottages) {
			List<TerminBoat> terminsTemp = new ArrayList<>();
			terminsTemp = terminService.finishedReservations(c.getId());
			for(TerminBoat t : terminsTemp) {
				cottageTermins.add(t);
			}
		}
		int jan = 0;
		int feb = 0;
		int mar = 0;
		int apr = 0;
		int may = 0;
		int jun = 0;
		int jul = 0;
		int aug = 0;
		int sep = 0;
		int oct = 0;
		int nov = 0;
		int dec = 0;
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
			for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
				String dateIter = sdf.format(date);
				if(dateIter.contains("Jan")) {
					jan++;
				} else if (dateIter.contains("Feb")){
					feb++;
				} else if (dateIter.contains("Mar")){
					mar++;
				} else if (dateIter.contains("Apr")){
					apr++;
				} else if (dateIter.contains("May")){
					may++;
				} else if (dateIter.contains("Jul")){
					jun++;
				} else if (dateIter.contains("Jul")){
					jul++;
				} else if (dateIter.contains("Aug")){
					aug++;
				} else if (dateIter.contains("Sep")){
					sep++;
				} else if (dateIter.contains("Oct")){
					oct++;
				} else if (dateIter.contains("Nov")){
					nov++;
				} else if (dateIter.contains("Dec")){
					dec++;
				}
			}
		}
		
		List<Integer> ret = new ArrayList<>();
		ret.add(jan);
		ret.add(feb);
		ret.add(mar);
		ret.add(apr);
		ret.add(may);
		ret.add(jun);
		ret.add(jul);
		ret.add(aug);
		ret.add(sep);
		ret.add(oct);
		ret.add(nov);
		ret.add(dec);
		
		return ret;
	}
	
	@Override
	public List<Integer> numberOfReservationsOnWeek(Long id) throws ParseException {
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		List<Boat> cottages = cottageService.findOwnerCottages(id);
		for(Boat c : cottages) {
			List<TerminBoat> terminsTemp = new ArrayList<>();
			terminsTemp = terminService.finishedReservations(c.getId());
			for(TerminBoat t : terminsTemp) {
				cottageTermins.add(t);
			}
		}
		int week1 = 0;
		int week2 = 0;
		int week3 = 0;
		int week4 = 0;
		int week5 = 0;
		
		for(TerminBoat t: cottageTermins) {
			String dayOfWeekStart = t.getStart().substring(0,2);
			int dayS = 0;
			switch(dayOfWeekStart) {
			case "01": dayS = 1;
			case "02": dayS = 2;
			case "03": dayS = 3;
			case "04": dayS = 4;
			case "05": dayS = 5;
			case "06": dayS = 6;
			case "07": dayS = 7;
			case "08": dayS = 8;
			case "09": dayS = 9;
			default: dayS = Integer.parseInt(dayOfWeekStart);
			}
			String dayOfWeekEnd = t.getEnd().substring(0,2);
			int dayE = 0;
			switch(dayOfWeekEnd) {
			case "01": dayE = 1;
			case "02": dayE = 2;
			case "03": dayE = 3;
			case "04": dayE = 4;
			case "05": dayE = 5;
			case "06": dayE = 6;
			case "07": dayE = 7;
			case "08": dayE = 8;
			case "09": dayE = 9;
			default: dayE = Integer.parseInt(dayOfWeekEnd);
			}
			if(dayS <= 7) {
				week1++;
				if(dayE > 7)
					week2++;
			} else if (dayS <= 14){
				week2++;
				if(dayE > 14)
					week3++;
			} else if (dayS <= 21){
				week3++;
				if(dayE > 21)
					week4++;
			} else if (dayS <= 28){
				week4++;
				if(dayE > 28)
					week5++;
			} else if (dayS > 28){
				week5++;
				if(dayE < 7)
					week1++;
			} 
		}
		
		List<Integer> ret = new ArrayList<>();
		ret.add(week1);
		ret.add(week2);
		ret.add(week3);
		ret.add(week4);
		ret.add(week5);
		
		return ret;
	}
	
	@Override
	public List<Integer> numberOfReservedDaysPerWeek(Long id) throws ParseException {
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		List<Boat> cottages = cottageService.findOwnerCottages(id);
		for(Boat c : cottages) {
			List<TerminBoat> terminsTemp = new ArrayList<>();
			terminsTemp = terminService.finishedReservations(c.getId());
			for(TerminBoat t : terminsTemp) {
				cottageTermins.add(t);
			}
		}
		int week1 = 0;
		int week2 = 0;
		int week3 = 0;
		int week4 = 0;
		int week5 = 0;
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
			for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
				String dayOfWeekStart = sdf.format(date).substring(0, 2);
				int dayS = 0;
				switch(dayOfWeekStart) {
				case "01": dayS = 1;
				case "02": dayS = 2;
				case "03": dayS = 3;
				case "04": dayS = 4;
				case "05": dayS = 5;
				case "06": dayS = 6;
				case "07": dayS = 7;
				case "08": dayS = 8;
				case "09": dayS = 9;
				default: dayS = Integer.parseInt(dayOfWeekStart);
				}
				
				if(dayS <= 7) {
					week1++;
				} else if (dayS <= 14){
					week2++;
				} else if (dayS <= 21){
					week3++;
				} else if (dayS <= 28){
					week4++;
				} else if (dayS > 28){
					week5++;
				} 
				
			}
			
		}
		List<Integer> ret = new ArrayList<>();
		ret.add(week1);
		ret.add(week2);
		ret.add(week3);
		ret.add(week4);
		ret.add(week5);
		
		return ret;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public List<Integer> numberOfReservationsOnDay(Long id) throws ParseException {
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		List<Boat> cottages = cottageService.findOwnerCottages(id);
		
		for(Boat c : cottages) {
			List<TerminBoat> terminsTemp = new ArrayList<>();
			terminsTemp = terminService.finishedReservations(c.getId());
			for(TerminBoat t : terminsTemp) {
				cottageTermins.add(t);
			}
		}
		
		int mon = 0;
		int tue = 0;
		int wed = 0;
		int thu = 0;
		int fri = 0;
		int sat = 0;
		int sun = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			Date start = sdf.parse(t.getStart());
			int day = start.getDay();
			
			if(day == 0) {
		    	mon++;
		    } else if (day == 1) {
		    	tue++;
		    } else if (day == 2) {
		    	wed++;
		    } else if (day == 3) {
		    	thu++;
		    } else if (day == 4) {
		    	fri++;
		    } else if (day == 5) {
		    	sat++;
		    } else if (day == 6) {
		    	sun++;
		    }   
			
		}
		
       List<Integer> ret = new ArrayList<>();
       ret.add(mon);
       ret.add(tue);
       ret.add(wed);
       ret.add(thu);
       ret.add(fri);
       ret.add(sat);
       ret.add(sun);
		
		return ret;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public List<Integer> numberOfReservedDays(Long id) throws ParseException {
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		List<Boat> cottages = cottageService.findOwnerCottages(id);
		
		for(Boat c : cottages) {
			List<TerminBoat> terminsTemp = new ArrayList<>();
			terminsTemp = terminService.finishedReservations(c.getId());
			for(TerminBoat t : terminsTemp) {
				cottageTermins.add(t);
			}
		}
		
		int mon = 0;
		int tue = 0;
		int wed = 0;
		int thu = 0;
		int fri = 0;
		int sat = 0;
		int sun = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
			for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
			    if(date.getDay() == 0) {
			    	mon++;
			    } else if (date.getDay() == 1) {
			    	tue++;
			    } else if (date.getDay() == 2) {
			    	wed++;
			    } else if (date.getDay() == 3) {
			    	thu++;
			    } else if (date.getDay() == 4) {
			    	fri++;
			    } else if (date.getDay() == 5) {
			    	sat++;
			    } else if (date.getDay() == 6) {
			    	sun++;
			    }
			}
		}
		
       List<Integer> ret = new ArrayList<>();
       ret.add(mon);
       ret.add(tue);
       ret.add(wed);
       ret.add(thu);
       ret.add(fri);
       ret.add(sat);
       ret.add(sun);
		
		return ret;
	}
	
	public Integer countReservationDays(TerminBoat termin) throws ParseException {
		
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
        
        return (int) difference;
	}
	
	@Override
	public List<Double> profitPerMonth(Long id) throws ParseException {
		List<TerminBoat> cottageTermins = new ArrayList<>();
		List<Boat> cottages = cottageService.findOwnerCottages(id);
		for(Boat c : cottages) {
			List<TerminBoat> terminsTemp = new ArrayList<>();
			terminsTemp = terminService.finishedReservations(c.getId());
			for(TerminBoat t : terminsTemp) {
				cottageTermins.add(t);
			}
		}
		
		double jan = 0;
		double feb = 0;
		double mar = 0;
		double apr = 0;
		double may = 0;
		double jun = 0;
		double jul = 0;
		double aug = 0;
		double sep = 0;
		double oct = 0;
		double nov = 0;
		double dec = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
	        //number of days reserved
	        int reservationDays = countReservationDays(t);
	        double dayPrice =  t.getPrice()/reservationDays;
	        
	        for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
				String dateIter = date.toString();
				if(dateIter.contains("Jan")) {
					jan += dayPrice;
				} else if (dateIter.contains("Feb")){
					feb += dayPrice;
				} else if (dateIter.contains("Mar")){
					mar += dayPrice;
				} else if (dateIter.contains("Apr")){
					apr += dayPrice;
				} else if (dateIter.contains("May")){
					may += dayPrice;
				} else if (dateIter.contains("Jul")){
					jun += dayPrice;
				} else if (dateIter.contains("Jul")){
					jul += dayPrice;
				} else if (dateIter.contains("Aug")){
					aug += dayPrice;
				} else if (dateIter.contains("Sep")){
					sep += dayPrice;
				} else if (dateIter.contains("Oct")){
					oct += dayPrice;
				} else if (dateIter.contains("Nov")){
					nov += dayPrice;
				} else if (dateIter.contains("Dec")){
					dec += dayPrice;
				}
			}
		}
		
		List<Double> ret = new ArrayList<>();
		ret.add(jan);
		ret.add(feb);
		ret.add(mar);
		ret.add(apr);
		ret.add(may);
		ret.add(jun);
		ret.add(jul);
		ret.add(aug);
		ret.add(sep);
		ret.add(oct);
		ret.add(nov);
		ret.add(dec);
		
		return ret;
	}
	
	@Override
	public List<Double> profitPerWeek(Long id) throws ParseException {
		List<TerminBoat> cottageTermins = new ArrayList<>();
		List<Boat> cottages = cottageService.findOwnerCottages(id);
		for(Boat c : cottages) {
			List<TerminBoat> terminsTemp = new ArrayList<>();
			terminsTemp = terminService.finishedReservations(c.getId());
			for(TerminBoat t : terminsTemp) {
				cottageTermins.add(t);
			}
		}
		double week1 = 0;
		double week2 = 0;
		double week3 = 0;
		double week4 = 0;
		double week5 = 0;
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
	        //number of days reserved
	        int reservationDays = countReservationDays(t);
	        double dayPrice =  t.getPrice()/reservationDays;
	        
	        for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
				
	        	String dayOfWeekStart = sdf.format(date).substring(0, 2);
				int dayS = 0;
				switch(dayOfWeekStart) {
				case "01": dayS = 1;
				case "02": dayS = 2;
				case "03": dayS = 3;
				case "04": dayS = 4;
				case "05": dayS = 5;
				case "06": dayS = 6;
				case "07": dayS = 7;
				case "08": dayS = 8;
				case "09": dayS = 9;
				default: dayS = Integer.parseInt(dayOfWeekStart);
				}
				
				if(dayS <= 7) {
					week1 += dayPrice;
				} else if (dayS <= 14){
					week2 += dayPrice;
				} else if (dayS <= 21){
					week3 += dayPrice;
				} else if (dayS <= 28){
					week4 += dayPrice;
				} else if (dayS > 28){
					week5 += dayPrice;
				} 
			}
			
		}
		
		List<Double> ret = new ArrayList<>();
		ret.add(week1);
		ret.add(week2);
		ret.add(week3);
		ret.add(week4);
		ret.add(week5);
		
		return ret;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public List<Double> profitPerDay(Long id) throws ParseException {
		List<TerminBoat> cottageTermins = new ArrayList<>();
		List<Boat> cottages = cottageService.findOwnerCottages(id);
		
		for(Boat c : cottages) {
			List<TerminBoat> terminsTemp = new ArrayList<>();
			terminsTemp = terminService.finishedReservations(c.getId());
			for(TerminBoat t : terminsTemp) {
				cottageTermins.add(t);
			}
		}
		
		double mon = 0;
		double tue = 0;
		double wed = 0;
		double thu = 0;
		double fri = 0;
		double sat = 0;
		double sun = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
	        //number of days reserved
	        int reservationDays = countReservationDays(t);
	        double dayPrice =  t.getPrice()/reservationDays;
	        
	        for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
			    if(date.getDay() == 0) {
			    	mon += dayPrice;
			    } else if (date.getDay() == 1) {
			    	tue += dayPrice;
			    } else if (date.getDay() == 2) {
			    	wed += dayPrice;
			    } else if (date.getDay() == 3) {
			    	thu += dayPrice;
			    } else if (date.getDay() == 4) {
			    	fri += dayPrice;
			    } else if (date.getDay() == 5) {
			    	sat += dayPrice;
			    } else if (date.getDay() == 6) {
			    	sun += dayPrice;
			    }
			}
		}
		
       List<Double> ret = new ArrayList<>();
       ret.add(mon);
       ret.add(tue);
       ret.add(wed);
       ret.add(thu);
       ret.add(fri);
       ret.add(sat);
       ret.add(sun);
		
		return ret;
	}

	
	@Override
	public Double getCottageProfit(String startDate, String endDate, Long id) throws ParseException {
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		cottageTermins = terminService.finishedReservations(id);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        double profit = 0;
        
		for(TerminBoat t : cottageTermins) {
			Date tStart = sdf.parse(t.getStart());
			Date tEnd = sdf.parse(t.getEnd());
			if(tStart.compareTo(start) >= 0 && tEnd.compareTo(end) <= 0) {
				profit += t.getPrice();
			}
		}
		
		return profit;
	}

	
	@Override
	public List<Integer> cottageReservationsOnMonth(Long id) throws ParseException {
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		cottageTermins = terminService.finishedReservations(id);
		int jan = 0;
		int feb = 0;
		int mar = 0;
		int apr = 0;
		int may = 0;
		int jun = 0;
		int jul = 0;
		int aug = 0;
		int sep = 0;
		int oct = 0;
		int nov = 0;
		int dec = 0;
		for(TerminBoat t: cottageTermins) {
			
			if(t.getStart().contains("Jan")) {
				jan++;
				if(t.getEnd().contains("Feb"))
					feb++;
			} else if (t.getStart().contains("Feb")){
				feb++;
				if(t.getEnd().contains("Mar"))
					mar++;
			} else if (t.getStart().contains("Mar")){
				mar++;
				if(t.getEnd().contains("Apr"))
					apr++;
			} else if (t.getStart().contains("Apr")){
				apr++;
				if(t.getEnd().contains("May"))
					may++;
			} else if (t.getStart().contains("May")){
				may++;
				if(t.getEnd().contains("Jun"))
					jun++;
			} else if (t.getStart().contains("Jul")){
				jun++;
				if(t.getEnd().contains("Jul"))
					jul++;
			} else if (t.getStart().contains("Jul")){
				jul++;
				if(t.getEnd().contains("Aug"))
					aug++;
			} else if (t.getStart().contains("Aug")){
				aug++;
				if(t.getEnd().contains("Sep"))
					sep++;
			} else if (t.getStart().contains("Sep")){
				sep++;
				if(t.getEnd().contains("Oct"))
					oct++;
			} else if (t.getStart().contains("Oct")){
				oct++;
				if(t.getEnd().contains("Nov"))
					nov++;
			} else if (t.getStart().contains("Nov")){
				nov++;
				if(t.getEnd().contains("Dec"))
					dec++;
			} else if (t.getStart().contains("Dec")){
				dec++;
				if(t.getEnd().contains("Jan"))
					jan++;
			}
		}
		
		List<Integer> ret = new ArrayList<>();
		ret.add(jan);
		ret.add(feb);
		ret.add(mar);
		ret.add(apr);
		ret.add(may);
		ret.add(jun);
		ret.add(jul);
		ret.add(aug);
		ret.add(sep);
		ret.add(oct);
		ret.add(nov);
		ret.add(dec);
		
		return ret;
	}

	
	@Override
	public List<Integer> cottageReservedDaysPerMonth(Long id) throws ParseException {
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		cottageTermins = terminService.finishedReservations(id);
		
		int jan = 0;
		int feb = 0;
		int mar = 0;
		int apr = 0;
		int may = 0;
		int jun = 0;
		int jul = 0;
		int aug = 0;
		int sep = 0;
		int oct = 0;
		int nov = 0;
		int dec = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
			for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
				String dateIter = sdf.format(date);
				if(dateIter.contains("Jan")) {
					jan++;
				} else if (dateIter.contains("Feb")){
					feb++;
				} else if (dateIter.contains("Mar")){
					mar++;
				} else if (dateIter.contains("Apr")){
					apr++;
				} else if (dateIter.contains("May")){
					may++;
				} else if (dateIter.contains("Jul")){
					jun++;
				} else if (dateIter.contains("Jul")){
					jul++;
				} else if (dateIter.contains("Aug")){
					aug++;
				} else if (dateIter.contains("Sep")){
					sep++;
				} else if (dateIter.contains("Oct")){
					oct++;
				} else if (dateIter.contains("Nov")){
					nov++;
				} else if (dateIter.contains("Dec")){
					dec++;
				}
			}
		}
		
		List<Integer> ret = new ArrayList<>();
		ret.add(jan);
		ret.add(feb);
		ret.add(mar);
		ret.add(apr);
		ret.add(may);
		ret.add(jun);
		ret.add(jul);
		ret.add(aug);
		ret.add(sep);
		ret.add(oct);
		ret.add(nov);
		ret.add(dec);
		
		return ret;
	}

	
	@Override
	public List<Integer> cottageReservationsOnWeek(Long id) throws ParseException {
		List<TerminBoat> cottageTermins = new ArrayList<>();
		cottageTermins = terminService.finishedReservations(id);
		
		int week1 = 0;
		int week2 = 0;
		int week3 = 0;
		int week4 = 0;
		int week5 = 0;
		
		for(TerminBoat t: cottageTermins) {
			String dayOfWeekStart = t.getStart().substring(0,2);
			int dayS = 0;
			switch(dayOfWeekStart) {
			case "01": dayS = 1;
			case "02": dayS = 2;
			case "03": dayS = 3;
			case "04": dayS = 4;
			case "05": dayS = 5;
			case "06": dayS = 6;
			case "07": dayS = 7;
			case "08": dayS = 8;
			case "09": dayS = 9;
			default: dayS = Integer.parseInt(dayOfWeekStart);
			}
			String dayOfWeekEnd = t.getEnd().substring(0,2);
			int dayE = 0;
			switch(dayOfWeekEnd) {
			case "01": dayE = 1;
			case "02": dayE = 2;
			case "03": dayE = 3;
			case "04": dayE = 4;
			case "05": dayE = 5;
			case "06": dayE = 6;
			case "07": dayE = 7;
			case "08": dayE = 8;
			case "09": dayE = 9;
			default: dayE = Integer.parseInt(dayOfWeekEnd);
			}
			if(dayS <= 7) {
				week1++;
				if(dayE > 7)
					week2++;
			} else if (dayS <= 14){
				week2++;
				if(dayE > 14)
					week3++;
			} else if (dayS <= 21){
				week3++;
				if(dayE > 21)
					week4++;
			} else if (dayS <= 28){
				week4++;
				if(dayE > 28)
					week5++;
			} else if (dayS > 28){
				week5++;
				if(dayE < 7)
					week1++;
			} 
		}
		
		List<Integer> ret = new ArrayList<>();
		ret.add(week1);
		ret.add(week2);
		ret.add(week3);
		ret.add(week4);
		ret.add(week5);
		
		return ret;
	}

	
	@Override
	public List<Integer> cottageReservedDaysPerWeek(Long id) throws ParseException {
		List<TerminBoat> cottageTermins = new ArrayList<>();
		cottageTermins = terminService.finishedReservations(id);
		
		int week1 = 0;
		int week2 = 0;
		int week3 = 0;
		int week4 = 0;
		int week5 = 0;
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
			for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
				String dayOfWeekStart = sdf.format(date).substring(0, 2);
				int dayS = 0;
				switch(dayOfWeekStart) {
				case "01": dayS = 1;
				case "02": dayS = 2;
				case "03": dayS = 3;
				case "04": dayS = 4;
				case "05": dayS = 5;
				case "06": dayS = 6;
				case "07": dayS = 7;
				case "08": dayS = 8;
				case "09": dayS = 9;
				default: dayS = Integer.parseInt(dayOfWeekStart);
				}
				
				if(dayS <= 7) {
					week1++;
				} else if (dayS <= 14){
					week2++;
				} else if (dayS <= 21){
					week3++;
				} else if (dayS <= 28){
					week4++;
				} else if (dayS > 28){
					week5++;
				} 
				
			}
			
		}
		List<Integer> ret = new ArrayList<>();
		ret.add(week1);
		ret.add(week2);
		ret.add(week3);
		ret.add(week4);
		ret.add(week5);
		
		return ret;
	}

	
	@Override
	public List<Integer> cottageReservationsOnDay(Long id) throws ParseException {
		
		List<TerminBoat> cottageTermins = new ArrayList<>();
		cottageTermins = terminService.finishedReservations(id);
		
		int mon = 0;
		int tue = 0;
		int wed = 0;
		int thu = 0;
		int fri = 0;
		int sat = 0;
		int sun = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			Date start = sdf.parse(t.getStart());
			@SuppressWarnings("deprecation")
			int day = start.getDay();
			
			if(day == 0) {
		    	mon++;
		    } else if (day == 1) {
		    	tue++;
		    } else if (day == 2) {
		    	wed++;
		    } else if (day == 3) {
		    	thu++;
		    } else if (day == 4) {
		    	fri++;
		    } else if (day == 5) {
		    	sat++;
		    } else if (day == 6) {
		    	sun++;
		    }   
			
		}
		
       List<Integer> ret = new ArrayList<>();
       ret.add(mon);
       ret.add(tue);
       ret.add(wed);
       ret.add(thu);
       ret.add(fri);
       ret.add(sat);
       ret.add(sun);
		
		return ret;
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public List<Integer> cottageReservedDays(Long id) throws ParseException {
		List<TerminBoat> cottageTermins = new ArrayList<>();
		cottageTermins = terminService.finishedReservations(id);
		
		int mon = 0;
		int tue = 0;
		int wed = 0;
		int thu = 0;
		int fri = 0;
		int sat = 0;
		int sun = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
			for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
			    if(date.getDay() == 0) {
			    	mon++;
			    } else if (date.getDay() == 1) {
			    	tue++;
			    } else if (date.getDay() == 2) {
			    	wed++;
			    } else if (date.getDay() == 3) {
			    	thu++;
			    } else if (date.getDay() == 4) {
			    	fri++;
			    } else if (date.getDay() == 5) {
			    	sat++;
			    } else if (date.getDay() == 6) {
			    	sun++;
			    }
			}
		}
		
       List<Integer> ret = new ArrayList<>();
       ret.add(mon);
       ret.add(tue);
       ret.add(wed);
       ret.add(thu);
       ret.add(fri);
       ret.add(sat);
       ret.add(sun);
		
		return ret;
	}

	
	@Override
	public List<Double> cottageProfitPerMonth(Long id) throws ParseException {
		List<TerminBoat> cottageTermins = new ArrayList<>();
		cottageTermins = terminService.finishedReservations(id);
		
		double jan = 0;
		double feb = 0;
		double mar = 0;
		double apr = 0;
		double may = 0;
		double jun = 0;
		double jul = 0;
		double aug = 0;
		double sep = 0;
		double oct = 0;
		double nov = 0;
		double dec = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
	        //number of days reserved
	        int reservationDays = countReservationDays(t);
	        double dayPrice =  t.getPrice()/reservationDays;
	        
	        for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
				String dateIter = date.toString();
				if(dateIter.contains("Jan")) {
					jan += dayPrice;
				} else if (dateIter.contains("Feb")){
					feb += dayPrice;
				} else if (dateIter.contains("Mar")){
					mar += dayPrice;
				} else if (dateIter.contains("Apr")){
					apr += dayPrice;
				} else if (dateIter.contains("May")){
					may += dayPrice;
				} else if (dateIter.contains("Jul")){
					jun += dayPrice;
				} else if (dateIter.contains("Jul")){
					jul += dayPrice;
				} else if (dateIter.contains("Aug")){
					aug += dayPrice;
				} else if (dateIter.contains("Sep")){
					sep += dayPrice;
				} else if (dateIter.contains("Oct")){
					oct += dayPrice;
				} else if (dateIter.contains("Nov")){
					nov += dayPrice;
				} else if (dateIter.contains("Dec")){
					dec += dayPrice;
				}
			}
		}
		
		List<Double> ret = new ArrayList<>();
		ret.add(jan);
		ret.add(feb);
		ret.add(mar);
		ret.add(apr);
		ret.add(may);
		ret.add(jun);
		ret.add(jul);
		ret.add(aug);
		ret.add(sep);
		ret.add(oct);
		ret.add(nov);
		ret.add(dec);
		
		return ret;
	}

	
	@Override
	public List<Double> cottageProfitPerWeek(Long id) throws ParseException {
		List<TerminBoat> cottageTermins = new ArrayList<>();
		cottageTermins = terminService.finishedReservations(id);
		
		double week1 = 0;
		double week2 = 0;
		double week3 = 0;
		double week4 = 0;
		double week5 = 0;
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
	        //number of days reserved
	        int reservationDays = countReservationDays(t);
	        double dayPrice =  t.getPrice()/reservationDays;
	        
	        for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
				
	        	String dayOfWeekStart = sdf.format(date).substring(0, 2);
				int dayS = 0;
				switch(dayOfWeekStart) {
				case "01": dayS = 1;
				case "02": dayS = 2;
				case "03": dayS = 3;
				case "04": dayS = 4;
				case "05": dayS = 5;
				case "06": dayS = 6;
				case "07": dayS = 7;
				case "08": dayS = 8;
				case "09": dayS = 9;
				default: dayS = Integer.parseInt(dayOfWeekStart);
				}
				
				if(dayS <= 7) {
					week1 += dayPrice;
				} else if (dayS <= 14){
					week2 += dayPrice;
				} else if (dayS <= 21){
					week3 += dayPrice;
				} else if (dayS <= 28){
					week4 += dayPrice;
				} else if (dayS > 28){
					week5 += dayPrice;
				} 
			}
			
		}
		
		List<Double> ret = new ArrayList<>();
		ret.add(week1);
		ret.add(week2);
		ret.add(week3);
		ret.add(week4);
		ret.add(week5);
		
		return ret;
	}

	
	@Override
	public List<Double> cottageProfitPerDay(Long id) throws ParseException {
		List<TerminBoat> cottageTermins = new ArrayList<>();
		cottageTermins = terminService.finishedReservations(id);
		
		double mon = 0;
		double tue = 0;
		double wed = 0;
		double thu = 0;
		double fri = 0;
		double sat = 0;
		double sun = 0;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		for(TerminBoat t: cottageTermins) {
			
			Calendar cal = Calendar.getInstance();  
	        try{  
	           cal.setTime(sdf.parse(t.getStart()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }  
	        Calendar calEnd = Calendar.getInstance();  
	        try{  
	        	calEnd.setTime(sdf.parse(t.getEnd()));  
	        }catch(ParseException e){  
	            e.printStackTrace();  
	        }
	        
	        Date tEnd = calEnd.getTime();
			
	        //number of days reserved
	        int reservationDays = countReservationDays(t);
	        double dayPrice =  t.getPrice()/reservationDays;
	        
	        for (Date date = cal.getTime(); !date.after(tEnd); cal.add(Calendar.DATE, 1), date = cal.getTime())
			{
			    if(date.getDay() == 0) {
			    	mon += dayPrice;
			    } else if (date.getDay() == 1) {
			    	tue += dayPrice;
			    } else if (date.getDay() == 2) {
			    	wed += dayPrice;
			    } else if (date.getDay() == 3) {
			    	thu += dayPrice;
			    } else if (date.getDay() == 4) {
			    	fri += dayPrice;
			    } else if (date.getDay() == 5) {
			    	sat += dayPrice;
			    } else if (date.getDay() == 6) {
			    	sun += dayPrice;
			    }
			}
		}
		
       List<Double> ret = new ArrayList<>();
       ret.add(mon);
       ret.add(tue);
       ret.add(wed);
       ret.add(thu);
       ret.add(fri);
       ret.add(sat);
       ret.add(sun);
		
		return ret;
	}
}
