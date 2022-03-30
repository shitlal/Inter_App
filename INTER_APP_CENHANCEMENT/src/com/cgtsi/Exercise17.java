package com.cgtsi;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Exercise17 {
   public static void main(String[] args) throws ParseException
   {
	   //guarantSanFromDt2Extension=31/01/2021
			//   guarantSanToDt6Extension=24/09/2021
	 //  cal.add(Calendar.YEAR, -2);
	  //    Date d2 =  cal.getTime();
	   SimpleDateFormat sdformat1 = new SimpleDateFormat("dd/MM/yyyy");
        String sanValue="28/10/2019";
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		 java.util.Date date = sdf.parse(sanValue);				 
		 java.sql.Date sqlDate_d = new java.sql.Date(date.getTime());	
        System.out.println("sqlDate_d......"+sqlDate_d);
        
        
        
        
           Date date_1 = Calendar.getInstance().getTime();
           DateFormat dateFormat_1 = new SimpleDateFormat("dd/MM/yyyy");
           String strDate_1 = dateFormat_1.format(date_1);
           Date d1 = sdformat1.parse(strDate_1);
           
          //System.out.println(date_1+"DDDDDDDDDDDDDDDDDDDDDDDDDD "+(String.valueOf(date_1)).length());
           
           Calendar cal = Calendar.getInstance();
           cal.add(Calendar.YEAR, -2);
           Date d2 =  cal.getTime();
          // Date d2 = cal.getTime(); 
           System.out.println("Main date d1 compare to d2::"+d1.compareTo(d2) +"   d1 "+d1 +"   d2 "+d2);
           
           if(d1.compareTo(d2) > 0)
           {    
               Date sanDate = dateFormat_1.parse(sanValue);
               Date d6 = d2;//dateFormat_1.parse(guarantSanFromDt2Extension); 
               System.out.println("   \nsanDate "+sanDate +"   \nd6 "+d6+"    \nsanction before  d6::"+sanDate.before(d6));
               if((sanDate.before(d6) ) || sanDate.equals(d6)) 
               {
               	System.out.println("show validation Throw  Amount Sanctioned Date cannot be before++++++++++++++++++++++++++++++++++++++= "+ d6);
                  // ActionError actionMessage = new ActionError((new StringBuilder()).append(" Amount Sanctioned Date cannot be before "+ guarantSanFromDt2Extension).toString());
                 //  errors.add("org.apache.struts.action.GLOBAL_ERROR", actionMessage);
               }else {
            	   System.out.println("Allow to submit");
               }
           }else {
        	   System.out.println("Sorry2");
           }
	   
	   
	   
	  
    }
}