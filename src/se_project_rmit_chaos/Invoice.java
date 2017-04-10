package se_project_rmit_chaos;

import java.util.*;
public class Invoice 
{
   private static int count = 1000;
   private Date d;
   private String ID;
   public String getID() { return ID; }
   public Date getDate() { return d; }
   public Invoice(Date d, Order o)
   {
        ID = "Inv"+ count++;
        this.d = d;
        System.out.print("\nID = " + ID + " Invoice Generated: Date = "
						 + d + " Price = " + o.price);
   }
}