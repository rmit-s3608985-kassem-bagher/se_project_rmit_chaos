package se_project_rmit_chaos;

import java.util.*;
public class Payment
{
   private Date d;
   private String type;
   public Date getDate() { return d; }
   public String getType() { return type; }

   public Payment(Date d, Order o, String type)
   {
     this.d = d;
     this.type = type;
     System.out.print("\nPayment recorded: Date = " + d +  
			" order ID = " + o.ID + " type " + type);
   }   
}