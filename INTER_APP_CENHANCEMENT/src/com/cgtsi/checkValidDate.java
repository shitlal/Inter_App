package com.cgtsi;
import java.util.Scanner;
class checkValidDate
{
	public static void main(String args[])
	{
	Scanner input = new Scanner (System.in);
	int t1=0,t2=0,y=0,m1=0,d=0;
	int[] m = new int[]{ 31,28,31,30,31,30,31,31,30,31,30,31};
        int[][] date= new int[2][3];
        String[] s = new String[] {"year","month","day"};
 
        for(int i=0;i<2;)
	{ 
           
	 System.out.println("enter date"+(i+1));
	  for(int j=0;j<3;j++) 
	  {
          
		System.out.println("enter "+s[j]);
          
	 	date[i][j]= input.nextInt();
          }
         int valid=check_valid_date(date[i],m);
	 if(valid==1)
	 {
            i++;
	 }
	else
	{
          System.out.println("error:enter valid date");
	
	}
 
	}
 
	if(date[0][0]>date[1][0])
        {
          System.out.println("error: invalid data");
		return;
         }
	else
	{
          t1=leapyear(date[0][0]);
	  t2=leapyear(date[1][0]);
          y=date[1][0]-date[0][0];
	   if(date[1][1]<date[0][1])
	    {
		y--;
		m1=12-date[0][1]+date[1][1];
		
		if(date[1][2]<date[0][2])
		{
		  m1--;
		  d=m[date[0][1]]-date[0][2]+date[1][2];
		}
		else
		   		  	
	  	{
		  d=date[1][2]-date[0][2];
		}
	    }
	    else
	     {
		m1=date[1][1]-date[0][1];
 
	     }
	}		
	
	 System.out.println(date[0][2]+"-"+date[0][1]+"-"+date[0][0]+"   to  "+date[1][2]+"-"+date[1][1]+"-"+date[1][0]);
	 
	 System.out.println(y+"years "+m1+"months "+d+"days");
 
   }
 
static int leapyear(int year)
{
int leap=0;
    if ((year % 400 == 0)||(year % 4 == 0 && year % 100 != 0)) //  check whether year is a leap year 
        {
            leap = 1;
        } 
    return leap;
}
 
static int monthvalidation(int month,int days,int m) 
{
    int i=0,j=0;
       if(month>=1 && month<=12)
        {
            i=1;
        } 
           
	if(days>=1 && days<=m)
        {
	 j=1;
	}
 	if(i+j==2)
	{
	return 1;
	}
	else
	{
	return 0;
	}
} 
 
   	
static int check_valid_date(int a[],int t[])    
{
   int leap,month,temp=0;
  
   if(a[0]>0)
   {
    leap=leapyear(a[0]);
    
    if(leap==1 && a[1]==2)
     temp=1;
     
     month=monthvalidation(a[1],a[2],t[a[1]]+temp);
     if(month==1)
      return 1;
	else
      return 0;
    }
    else
    {
	return 0;
    }
 }
}

