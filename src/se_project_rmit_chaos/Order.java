package se_project_rmit_chaos;

import java.util.*;
class Order 
{
   protected String ID;
   protected Date d;
   protected double price; 
   protected Customer cust;
   private ArrayList<OrderLine> list = new ArrayList<OrderLine>();
   private Scanner scan = new Scanner(System.in);
   private Invoice inv;
   private Payment payment;
   private char status;

   public String getID() { return ID; }
   public char getStatus(){ return status; }

   public Date getDate(){ return d;}

   public void print()
   {
      computePrice();
      System.out.printf("%-20s %-20s\n","ID",ID);
      System.out.printf("%-20s %-20s\n","Customer",cust.getName());
      System.out.printf("%-20s %-20s\n\n","Date",d.toString());
      for (int i=0; i<list.size(); i++)
        list.get(i).print();
      System.out.printf("\n%-20s %-20s\n","total price",price);
      System.out.printf("%-20s %-20c\n","Status",status);
      if ( status == 'D' || status == 'F')
      {
          System.out.printf("%-20s %-20s\n","invoice ID",inv.getID());
          System.out.printf("%-20s %-20s\n","invoice Date",
					inv.getDate().toString());
      }
      if ( status == 'F')
      {
          System.out.printf("%-20s %-20s\n",
				"Payment Date",payment.getDate().toString());
          System.out.printf("%-20s %-20s\n",
				"Payment Type",payment.getType());
      }
   }


   public Order(Date d, String ID,Customer c,Product ps[],int qtys[]) 
   {
       this.d = d;
       this.ID = ID;
       cust = c; 
       for (int i=0; i<ps.length; i++)
          list.add(new OrderLine(ps[i],qtys[i],this));
   }

   public Order(ArrayList<Customer> cList, ArrayList<Product> pList)
   {
        d = MyDate.getDate("Enter date of Order: ");
        System.out.print("Enter Order ID : ");
        ID = scan.nextLine();
        for (int i=0; i<cList.size(); i++)
           System.out.println("  " + i + "  " + cList.get(i).getName());    
        int i;  
        do {      
             System.out.print("Enter Customer index : ");
             i = scan.nextInt();
        } while ( i  < 0 || i >= cList.size());
        cust = cList.get(i);
        String resp;       
        do {
           for ( i=0; i<pList.size(); i++)
              System.out.println("   " + i + "   "
				 + pList.get(i).getName());
           do {      
             System.out.print("Enter Product index : ");
             i = scan.nextInt();
           } while ( i  < 0 || i >= pList.size());
           System.out.print("Enter 	qty : ");
           int qty = scan.nextInt();
           list.add(new OrderLine(pList.get(i),qty,this));
           scan.nextLine();
           System.out.print("Any more items ? : Y/N");
           resp = scan.nextLine();
        }  while (   resp.compareTo("Y") == 0);       
        status = 'W';   
   }

   public void computePrice()
   {
           price = 0;
           for (int i=0; i<list.size(); i++)
              price += list.get(i).getPrice(); 
           price -= getDiscount(price);
   }

   public double getDiscount(double price)
   {
//       double disc = cust.getDisVal(price);
       double disc = 0;
       return disc;
   }
   public void despatch()
   {
        if (status == 'W')
        {
           computePrice();
           Date date = new Date();
           inv = new Invoice(date,this);
           status = 'D';
        } 
        else System.out.println("Attempt to despatch failed");
   }

   public void cancel()
   {
      if (status == 'W')
      {
         status = 'C';
         System.out.println("Order Cancelled");
      }
      else System.out.println("Attempt to cancel failed");    
   }

   public void receive(String type)
   {
        if (status == 'D')
        {
           Date date = new Date();
           payment = new Payment(date,this,type);
           status = 'F';
       } 
       else System.out.println("Attempt to receive payment failed");    

   }

}

