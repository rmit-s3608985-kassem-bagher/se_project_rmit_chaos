package se_project_rmit_chaos;

import java.util.*;
public class Product 
{
   private String name;
   private double unitPrice;
   private double wholesaleUnitPrice;
   private int wholesaleQty;
   private ArrayList<OrderLine> ordLines = new ArrayList<OrderLine>();

   public Product(String name, double unitPrice, 
	double wholesaleUnitPrice, int wholesaleQty)
   {
        this.name = name;
        this.unitPrice = unitPrice;
        this.wholesaleUnitPrice = wholesaleUnitPrice;
        this.wholesaleQty = wholesaleQty;
   }

   public ArrayList<OrderLine> getOrderLines()
   {
        return ordLines;
   }

   public void addOrderLine(OrderLine ordLine)
   {
       ordLines.add(ordLine);
   }

   public double price(double amt)
   {        
        if ( amt >=  wholesaleQty )
            return wholesaleUnitPrice * amt;
        else
            return unitPrice * amt;
   }
   public String getName(){ return name; }
}



