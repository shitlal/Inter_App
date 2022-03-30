package com.cgtsi.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class Exercise17 {
   public static void main(String[] args) throws ParseException
   {

	
	   //=====================================================================
	
	  SimpleDateFormat sdformat = new SimpleDateFormat("dd/MM/yyyy");
      Calendar cal = Calendar.getInstance();
      Date cdate = cal.getTime();
      // get next year
    //  cal.add(Calendar.YEAR, 1); 
      cal.add(Calendar.YEAR, -2);      
      Date p2year = cal.getTime();      
      String sysDate = sdformat.format(cdate);       
      //System date
      String splitSysD[]=sysDate.split("/");
      String sFdate=splitSysD[2]+splitSysD[1]+splitSysD[0];
      Integer sysFinalDate=Integer.valueOf(sFdate);     
      String strP2Date = sdformat.format(p2year);
      String dateInString ="29/09/2019";//SanctionDate		
      Date sanctionDate = sdformat.parse(dateInString);
      String fDate=sdformat.format(p2year); //CurrentDate+2 years
      String splitFDate[]=strP2Date.split("/");
      String mFdate=splitFDate[2]+splitFDate[1]+splitFDate[0];
      Integer y2Date=Integer.valueOf(mFdate);
      String splitTDate[]=dateInString.split("/");
      String mTdate=splitTDate[2]+splitTDate[1]+splitTDate[0];
      Integer sancTdate=Integer.valueOf(mTdate);
      if(sysFinalDate>=y2Date && y2Date<=sancTdate) {
    	  System.out.println("Treu Con===");
      }else {
    	  System.out.println("Show Validation==");
      }
      //======================================================================
    
    }
}