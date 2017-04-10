package se_project_rmit_chaos;

import java.util.*;

class MyDate
{
   private static Scanner scan = new Scanner(System.in);
   public static Date getDate(String message)
   {
      System.out.print(message);      
      System.out.print("Day : ");
      int day = scan.nextInt();
      System.out.print("Month : ");
      int month = scan.nextInt();
      System.out.print("Year : ");
      int year = scan.nextInt();

      Calendar cal = Calendar.getInstance();
      cal.set(year,month,day);
      return cal.getTime();
   }
   public static Date getDate(int d, int m, int y)
   {
      Calendar cal = Calendar.getInstance();
      cal.set(y,m,d);
      return cal.getTime();
   }
}