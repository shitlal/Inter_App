package com.cgtsi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TESTdT {
	  public static void main(String[] args) throws ParseException {
		 /*			
	      SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
	      Date d1 = sdformat.parse("2020-05-01");   //npa
	      Date d2 = sdformat.parse("2020-04-01");
	      Date d3 = sdformat.parse("2020-06-06");
	      System.out.println("The date 1  5NPA DATE is: " + sdformat.format(d1));
	      System.out.println("The date 2 HARD is: " + sdformat.format(d2));
	     if(d1.compareTo(d2) > 0) {
	         System.out.println(d1+ " INput date is greater "+ d2);
	         
	      } else if(d1.compareTo(d2) < 0) {
	         System.out.println(d1+ " INput date is smaller "+d2);
	         
	      } else if(d1.compareTo(d2) == 0) {
	         System.out.println("Both dates are equal");
	      }
	      
	      
	     // System.out.println("d1"+d1   +"d2:  "+d2+  "  d3:  "+d3);
	      if((d1.compareTo(d2) > 0) && (d1.compareTo(d3) < 0)) {
		         System.out.println("NPA occurs between date");
		       
		      } else  {
		         System.out.println("invalid date");
		      }
		      */
		  
		  SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
	      Date d1 = sdformat.parse("10/10/2017");   //npa
	      Date d2 = sdformat.parse("2020-04-01");
	      Date d3 = sdformat.parse("2020-06-06");
	      System.out.println("The date 1  5NPA DATE is: " + sdformat.format(d1));
	      System.out.println("The date 2 HARD is: " + sdformat.format(d2));
	     if(d1.compareTo(d2) > 0) {
	         System.out.println(d1+ " INput date is greater-------------------------- "+ d2);
	         
	      } else if(d1.compareTo(d2) < 0) {
	         System.out.println(d1+ " INput date is smaller-------------------------- "+d2);
	         
	      } else if(d1.compareTo(d2) == 0) {
	         System.out.println("Both dates are equal--------------------------------");
	      }
	      
	      
	      System.out.println("d1"+d1   +"\nd2:  "+d2+  " \nd3:  "+d3);
	      if((d1.compareTo(d2) > 0) && (d1.compareTo(d3) < 0)) {
		         System.out.println("NPA occurs between date");
		       
		      } else  {
		         System.out.println("invalid date");
		      }
	   }

}
