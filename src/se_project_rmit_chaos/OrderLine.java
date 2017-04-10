package se_project_rmit_chaos;

public class OrderLine 
{
   private int qty; 
   private Product p;
   private Order order;

   public void print()
   {
      System.out.printf("%-20s %-20s %-20s %10d\n",
			"Product name ",p.getName(),"Quantity ",qty);      
   }

   public OrderLine(Product p, int qty, Order order)
   {
       this.p = p;
       p.addOrderLine(this);
       this.qty = qty; 
       this.order = order;
   }
   public Order getOrder()
   {
      return order;
   }
   
   public double getPrice()
   {
       double val =  p.price(qty);
       return val;
   }
}
