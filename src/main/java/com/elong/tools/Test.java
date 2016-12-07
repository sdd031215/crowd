package com.elong.tools;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Test {

	String path = "/home/hotel-revenuetest/dong.shao/rerank/test";

	public static void main(String[] args) throws Exception {

//		System.out.println(LocalDateTime.parse("2016-01-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		LocalDateTime arrivalDate = LocalDateTime.now();
//		try {
//			LocalDateTime ldt = LocalDateTime.parse("2016-11-11 23:39:46",
//					DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
//			String landing = ldt.format(format);
//			System.out.printf("Arriving at : %s %n", landing);
//		} catch (DateTimeException ex) {
//			System.out.printf("%s can't be formatted!%n", arrivalDate);
//			ex.printStackTrace();
//		}
		LocalDate today = LocalDate.now();
        System.out.println("Current Date="+today.plusDays(-1));
 
        //Creating LocalDate by providing input arguments
        LocalDate firstDay_2014 = LocalDate.of(2014, Month.JANUARY, 1);
        System.out.println("Specific Date="+firstDay_2014);
 
        //Try creating date by providing invalid inputs
        //LocalDate feb29_2014 = LocalDate.of(2014, Month.FEBRUARY, 29);
        //Exception in thread "main" java.time.DateTimeException: 
        //Invalid date 'February 29' as '2014' is not a leap year
 
        //Current date in "Asia/Kolkata", you can get it from ZoneId javadoc
        LocalDate todayKolkata = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        System.out.println("Current Date in IST="+todayKolkata);
 
        //java.time.zone.ZoneRulesException: Unknown time-zone ID: IST
        //LocalDate todayIST = LocalDate.now(ZoneId.of("IST"));
 
        //Getting date from the base date i.e 01/01/1970
        LocalDate dateFromBase = LocalDate.ofEpochDay(365);
        System.out.println("365th day from base date= "+dateFromBase);
 
        LocalDate hundredDay2014 = LocalDate.ofYearDay(2014, 100);
        System.out.println("100th day of 2014="+hundredDay2014);
		
	}

	public static String toString1(Object[] a, String split) {
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";
		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			b.append(String.valueOf(a[i]));
			if (i == iMax)
				return b.append(']').toString();
			b.append(split);
		}
	}
}
