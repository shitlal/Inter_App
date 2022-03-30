package com.cgtsi;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
 
public class AgeCalculator
{
   private int promAgeDr(Date birthDate)
   {
      int years = 0;
      int months = 0;
      int days = 0;
      Calendar birthDay = Calendar.getInstance();
      birthDay.setTimeInMillis(birthDate.getTime());
      long currentTime = System.currentTimeMillis();
      Calendar now = Calendar.getInstance();
      now.setTimeInMillis(currentTime);
      years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
      int currMonth = now.get(Calendar.MONTH) + 1;
      int birthMonth = birthDay.get(Calendar.MONTH) + 1;
 
      //Get difference between months
      months = currMonth - birthMonth;
      if (months < 0)
      {
         years--;
         months = 12 - birthMonth + currMonth;
         if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
            months--;
      } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
      {
         years--;
         months = 11;
      } 
      if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
         days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
      else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
      {
         int today = now.get(Calendar.DAY_OF_MONTH);
         now.add(Calendar.MONTH, -1);
         days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
      } 
      else
      {
         days = 0;
         if (months == 12)
         {
            years++;
            months = 0;
         }
      }
      return years;
   } 
   public  void main(String[] args) throws ParseException
   {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date birthDate = sdf.parse("13/08/1989");     
      int age = promAgeDr(birthDate);
      System.out.println("Total age"+age);
   }
}

