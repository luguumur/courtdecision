/* 
 * File Name: 		CalendarUtil.java
 * 
 * Created By: 		U.Narankhuu
 * Created Date: 	Apr 28, 2011
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Apr 28, 2011 1.0.0 			U.Narankhuu						Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */
package mn.mcs.electronics.court.util;

import java.util.Calendar;
import java.util.Date;

import mn.mcs.electronics.court.enums.Gender;

public class CalendarUtil {

	private Calendar cal;

	private long daysToMillis(double days) {
		long l = (long) days;

		l *= 24 * 60 * 60 * 1000;

		return l;
	}

	private long hoursToMillis(double hours) {
		long l = (long) hours;

		l *= 60 * 60 * 1000;

		return l;
	}

	public CalendarUtil() {
		cal = Calendar.getInstance();
	}

	public CalendarUtil(Date date) {
		cal = Calendar.getInstance();

		if (date != null)
			cal.setTime(date);
	}

	public int getYear() {
		return cal.get(Calendar.YEAR);
	}

	public void setDate(Date date) {
		cal.setTime(date);
	}

	public Date getDate() {
		return cal.getTime();
	}

	public void addMonth(int months) {
		cal.add(Calendar.MONTH, months);
	}

	public void addYear(int years) {
		cal.add(Calendar.YEAR, years);
	}

	public Date subYear(int years) {
		 cal.add(Calendar.YEAR, -years);
		 
		return cal.getTime();
	}
	
	public int getDiffYear(Calendar cal1) {

		int year1 = cal.get(Calendar.YEAR);
		int year2 = cal1.get(Calendar.YEAR);

		return (year2 - year1);
	}

	public void addDaysAjliin(double days) {

		int d1 = cal.get(Calendar.SATURDAY);
		d1 += cal.get(Calendar.SUNDAY);

		addDays(days);

		int d2 = cal.get(Calendar.SATURDAY);
		d2 += cal.get(Calendar.SUNDAY);

		d2 = d1 - d2;
		addDays(d2);
	}

	public void addDays(double days) {
		long l = cal.getTimeInMillis();

		l += this.daysToMillis(days);

		cal.setTimeInMillis(l);
	}

	public void addHours(double hours) {
		long l = cal.getTimeInMillis();

		l += this.hoursToMillis(hours);

		cal.setTimeInMillis(l);
	}

	public Calendar getCal() {
		return cal;
	}

	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	public void setDayTime(int h, int m, int s) {

		cal.set(Calendar.HOUR, h);

		cal.set(Calendar.MINUTE, m);

		cal.set(Calendar.SECOND, s);
	}

	public static String[] timeToStr(Date date) {
		String[] strArr = new String[2];
		Integer h, m;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		h = cal.get(Calendar.HOUR_OF_DAY);
		m = cal.get(Calendar.MINUTE);
		strArr[0] = h.toString();
		strArr[1] = m.toString();
		return strArr;
	}

	/* May 10, 2011 У.Наранхүү Start */
	public void restartYear() {
		this.setDayTime(0, 0, 0);

		cal.set(Calendar.MONTH, Calendar.JANUARY);

		cal.set(Calendar.DAY_OF_MONTH, 1);
	}

	/* May 10, 2011 У.Наранхүү End */

	/* May 17, 2011 Z.Zolbayar Start */
	public static Long calculateDifferent(Date hasagdagch, Double shalgahHugatsaa, Date hasagch) {

		Calendar earlier = Calendar.getInstance();
		Calendar later = Calendar.getInstance();

		earlier.setTime(hasagdagch);
		later.setTime(hasagch);

		earlier.add(Calendar.DAY_OF_MONTH, shalgahHugatsaa.intValue());

		Long diff;
		if (hasagdagch == null || hasagch == null) {
			return 0l;
		}
		diff = (later.getTime().getTime() - earlier.getTime().getTime())
				/ (24 * 3600 * 1000);
		return diff;
	}

	/* May 17, 2011 Z.Zolbayar End */
	/* Jul 18, 2011, 2011 Б.Дэлгэрсүрэн Start */
	public static Date getUmnuhJil() {
		/*
		 * 12 сарын 25наас хойш байвал он нь хэвээрээ үлдэх,урагш байвал оноос
		 * нь нэг хасагдах
		 */
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int date = cal.get(Calendar.DAY_OF_MONTH);
		int year;
		if (month == 11 && date >= 25) {
			year = cal.get(Calendar.YEAR);
		} else {
			year = cal.get(Calendar.YEAR) - 1;
		}
		month = 11;
		date = 25;
		cal.set(year, month, date);
		Date day = cal.getTime();
		return day;
	}
	/* Жилийн зөрүүг олох*/
	public static Long calculateDifferent(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		Calendar cal2 = Calendar.getInstance();
		Integer d = cal2.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		return d.longValue();
	}
	/* Jul 18, 2011, 2011 Б.Дэлгэрсүрэн End */
	
	public static String calculateMill(Long allMill) {
		
		Long day;
		Long month;
		Long year;
		
		day= allMill / (1000 * 60 * 60 * 24);
		
		Long tmpDay=day;
		
		year = day / 365;
		day = tmpDay - year * 365;
		month = day / 30;
		day = day - month * 30;
			
		String strDays=((year>0)? year+"ж ":"")+((month>0)? month+"с ":"")+((day>0)? day+"х ":"");
		return (strDays.equals(""))?"0x ":strDays;
	}

	public static String convertDay(Long allDay) {
		
		Long day;
		Long month;
		Long year;
				
		Long tmpDay=allDay;
		
		year = allDay / 365;
		day = tmpDay - year * 365;
		month = day / 30;
		day = day - month * 30;
			
		String strDays=((year>0)? year+"ж ":"")+((month>0)? month+"с ":"")+((day>0)? day+"х ":"");
		return (strDays.equals(""))?"0x ":strDays;
	}

	public static String getOverDays(Long allMill,Gender gender,CalendarUtil oldCal) {
		
		Long day=0l;
		Long month;
		Long year;
		
		Long ajillahJil;
		
		Long days= allMill / (1000 * 60 * 60 * 24);
		Calendar todayCal = Calendar.getInstance();
		
		Integer d = todayCal.get(Calendar.YEAR) - oldCal.getYear();
					
		if(d>0)
			day=d.longValue();
		else{
			
			ajillahJil=days/365;
			
			if(gender!=null && gender.equals(Gender.FEMALE)){
				
				if(ajillahJil>=20)
					day=days-20*365;
			}else{
				
				if(ajillahJil>=25)
					day=days-25*365;
			}
		}
		Long tmpDay=day;
		
		year = day / 365;
		day = tmpDay - year * 365;
		month = day / 30;
		day = day - month * 30;
		
		String strDays=((year>0)? year+"ж ":"")+((month>0)? month+"с ":"")+((day>0)? day+"х ":"");
		return (strDays.equals(""))?"0x ":strDays;
	}
	
	
}
