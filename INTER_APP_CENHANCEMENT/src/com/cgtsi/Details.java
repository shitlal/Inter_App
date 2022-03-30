package com.cgtsi;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
class Details{
   public static void main(String args[])throws ParseException
   {
      Details obj = new Details();
      String str= obj.datecheckcmp("dd/MM/yyyy", "dd/MM/yyyy", "27/12/2013", "28/11/2013");
      System.out.println(str);
   }
   String datecheckcmp(String fromDateFormat, String toDateFormat,
   String fromdate, String todate)throws ParseException
   {
    //  String CheckFormat = "dd-MMM-yyyy";
	   String CheckFormat = "dd/MM/yyyy";
      String dateStringFrom;
      String dateStringTo;
      Date DF = new Date();
      Date DT = new Date();
      int flagtodate=0;
      int flagfromdate=0;
      try
      {
         //DateFormatdf = DateFormat.getDateInstance(DateFormat.SHORT);
         DateFormat FromDF = new SimpleDateFormat(fromDateFormat);
         FromDF.setLenient(false);  // this is important!
         Date FromDate = FromDF.parse(fromdate);
         dateStringFrom = new SimpleDateFormat(CheckFormat).format(FromDate);
         DateFormat FromDF1 = new SimpleDateFormat(CheckFormat);
         DF=FromDF1.parse(dateStringFrom);
         System.out.println("fromDateFormat is ok = " + dateStringFrom);
      }
      catch (ParseException e)
      {
         flagfromdate = 1;
      }
      catch (IllegalArgumentException e)
      {
         flagfromdate = 1;
      }
      try
      {
         //DateFormatdf = DateFormat.getDateInstance(DateFormat.SHORT);
         DateFormat ToDF = new SimpleDateFormat(toDateFormat);
         ToDF.setLenient(false); // this is important!
         Date ToDate = ToDF.parse(todate);
         dateStringTo = new SimpleDateFormat(CheckFormat).format(ToDate);
         DateFormat ToDF1 = new SimpleDateFormat(CheckFormat);
         DT=ToDF1.parse(dateStringTo);
         System.out.println("toDateFormat is ok = " + dateStringTo); 
      }
      catch (ParseException e)
      {
         flagtodate=1;
      }
      catch (IllegalArgumentException e)
      {
         flagtodate=1;
      }
      if(flagfromdate == 0 &&flagtodate==0)
      {
         if(DF.equals(DT))
         {
    	    // if the date is same
            return "FromDate and ToDate are same"; 
         }
         else if(DF.before(DT))
         {
    	    //if the from date is before the to date
            return "FromDate is less than ToDate"; 
         }
         else
         {
            // if the date from is after the to date
           return "FromDate is greater than the ToDate"; 
         }
      }
   return "Error";
   }
}