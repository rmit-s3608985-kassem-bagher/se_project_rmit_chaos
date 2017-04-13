package se_project_rmit_chaos;

import java.util.*;
public class Company
{
   private ArrayList<Order> orders = new ArrayList<Order>();
   private ArrayList<Customer> custs = new ArrayList<Customer>();
   private ArrayList<Product> prods = new ArrayList<Product>();
   private ArrayList<Employee> emps = new ArrayList<Employee>();
   private Employee employee = null;

   Scanner scan = new Scanner(System.in);
   
   public ArrayList<Order> getOrders() { return orders; }  
   public ArrayList<Customer> getCustomers() { return custs; }  
   public ArrayList<Product> getProducts() { return prods; }  
   public ArrayList<Employee> getEmployees() { return emps; }  


   public Customer getCustomer(String name)
   {
       for (int i=0; i<custs.size(); i++)
          if ( custs.get(i).getName().compareTo(name)==0)
             return custs.get(i);
       return null;
   }
   public Product getProduct(String name)
   {
       for (int i=0; i<prods.size(); i++)
          if ( prods.get(i).getName().compareTo(name)==0)
             return prods.get(i);
       return null;
   }

   public Product[] getProducts(String[] names)
   {
       Product p[] = new Product[names.length];
       int k=0;
       for (int i=0; i<names.length; i++) 
          for (int j=0; j<prods.size(); j++)
            if ( prods.get(j).getName().compareTo(names[i])==0)
              p[k++] = prods.get(j);
       return p;
   }
    

   public char menu()
   {
      System.out.println("\n\n\n\t\tOrder Processing System\n");
      System.out.println("\tPrint All Orders for Product         0" );
      System.out.println("\tNew Order                            1" );
      System.out.println("\tAdd New Customer                     2" );
      System.out.println("\tAdd New Product                      3" );
      System.out.println("\tDespatch Order and Generate Invoice  4" );
      System.out.println("\tCancel Order                         5" );
      System.out.println("\tReceive Payment                      6" );
      System.out.println("\tPrint Orders                         7" );
      System.out.println("\tLogin                                8" );
      System.out.println("\tExit                                 9" );
      System.out.println("\n\t**************************************");
      System.out.print("\tYour choice : " );
      char ch = scan.nextLine().charAt(0);
      return ch;
   }

   public static void main(String args[])
   {
       Company f = new Company();
       f.go();
   }

   public void addOrder()
   {
      Order o = new Order(custs,prods); 
      orders.add(o);
   }
   public void add(Order o)
   {
      orders.add(o);
   }

   public void addCustomer()
   {
      System.out.print("Enter customer name : ");        
      String name = scan.nextLine();
      System.out.print("Enter rate of discount : ");     
      double rate = scan.nextDouble();
      add(new Customer(name,rate));
      scan.nextLine();
   }
   public void add(Customer c)
   {  custs.add(c);
   }


   public ArrayList<Order> getProductOrders(String productName, 
                                          Date fromDate, Date toDate)
   {
      ArrayList<Order> orders = new ArrayList<Order>();
      for (int i=0; i< prods.size(); i++)
         if ( prods.get(i).getName().compareTo(productName)==0)
         {
           ArrayList<OrderLine> ordLines = prods.get(i).getOrderLines(); 
           for (int j=0; j<ordLines.size(); j++)  
           {        
               Order order = ordLines.get(j).getOrder();

               if ( order.getDate().after(fromDate) && 
                   order.getDate().before(toDate))
               orders.add(order);
           }
         }
      return orders;
   }

   public void printProductOrders()
   {
      System.out.print("Enter product name : ");        
      String name = scan.nextLine();
      Date startDate = MyDate.getDate("Enter start date : ");
      Date endDate = MyDate.getDate("Enter end date : ");
      ArrayList<Order> orders=getProductOrders(name,startDate,endDate);      
      for (int i=0; i<orders.size(); i++)
          orders.get(i).print();
   }               

   public void addProduct()
   {
      System.out.print("Enter product name : ");        
      String name = scan.nextLine();
      System.out.print("Enter unit price : ");       
      double up = scan.nextDouble();
      System.out.print("Enter wholesale price : ");     
      double wsp = scan.nextDouble();
      System.out.print("Enter wholesale amt : ");       
      int wsamt = scan.nextInt();
      add(new Product(name,up,wsp,wsamt));
      scan.nextLine();
   }

   public void add(Product p)
   {
      prods.add(p);
   }
   

   public void login()
   {
      System.out.print("Enter username : ");  
      String un = scan.nextLine();
      System.out.print("Enter password : ");     
      String pw = scan.nextLine();
      int i;
      for (i=0; i< emps.size(); i++)
        if ( emps.get(i).getUsername().compareTo(un)==0 && 
                  emps.get(i).getPassword().compareTo(pw)==0)
        {
           employee = emps.get(i);
           System.out.println("Welcome " + employee.getName() +
                " Access Rights: [" + employee.getClass()+ "]");
           break;
        }
      if ( i == emps.size() )
         System.out.println("Invalid username/password"); 
      System.out.println("Press enter to continue");
      scan.nextLine();
   }

   public void waitForRes()
   {
      System.out.println("\nPress enter to continue");
      scan.nextLine();
   }

   public void cancelOrder()
   {
      System.out.print("Enter Order ID : ");
      String ID = scan.nextLine();
      int i;
      for (i=0; i<orders.size(); i++)
         if ( orders.get(i).getID().compareTo(ID) == 0)
         {
            orders.get(i).cancel(); break;
         }  
      if ( i == orders.size() )
         System.out.println("No Order found with given ID"); 
      waitForRes();
   }


   public void despatchOrder()
   {
      System.out.print("Enter Order ID : ");
      String ID = scan.nextLine();
      int i;
      for (i=0; i<orders.size(); i++)
         if ( orders.get(i).getID().compareTo(ID) == 0)
         {
             Order ord = orders.get(i);
             if ( ord.getStatus() == 'D')
                System.out.print("Order already depatched\n");
             else
                ord.despatch();
             break;

         }       
         if ( i == orders.size() )
            System.out.print("No Order found with ID = " + ID + "\n");                     
   }


   public void printOrders()
   {
      if ( orders.size() == 0)
         System.out.println("No current orders");
      else  
         System.out.println("Printing orders total number of orders=" + orders.size());                     
      for (int i=0; i<orders.size(); i++)
      {
          System.out.println("Order " + (i + 1));
          orders.get(i).print();
          waitForRes();
      }

   }

   public void receivePayment()
   {
      System.out.print("Enter Order ID : ");
      String ID = scan.nextLine();
      System.out.print("Enter Payment type : ");
      String type = scan.nextLine();

      int i;
      for (i=0; i<orders.size(); i++)
         if ( orders.get(i).getID().compareTo(ID) == 0)
         {
             Order ord = orders.get(i);
             if ( ord.getStatus() == 'F')
                System.out.print("Payment already received\n");
             else
                ord.receive(type);
             break;

         }       
         if ( i == orders.size() )
             System.out.print("No Order found with ID = " + ID + "\n");                     

   }

   public void go()
   {
      char ch;
      do { 
        ch = menu(); 
        if ( ch >= '1' && ch <= '6' && employee == null)
        {  System.out.print("Login first before using choices 1 to 5"); 
		waitForRes(); 
        }
        else if ( ch == '1' && employee != null && 
				!(employee instanceof Manager) ) 
        { System.out.print("Login as Manager to Add Order"); 
          waitForRes(); } 
          else {
            switch (ch)
            {
              case '0' : printProductOrders();
                   break;
              case '1' : addOrder();
                   break;
              case '2' : addCustomer();
                   break;
              case '3' : addProduct();
                   break;
              case '4' : despatchOrder();
                   break;
              case '5' : cancelOrder();
                   break;
              case '6' : receivePayment();
                   break;
              case '7' : printOrders();
                   break;
              case '8' : login();
                   break;
            }
          }
       }  while ( ch != '9');         
   }


   Company()
   {
        custs.add(new Customer("Coles",10));
        custs.add(new Customer("Target",12));

        prods.add(new Product("Engine",12,10,100));
        prods.add(new Product("Axle",15,10,50));
        prods.add(new Product("Compressor",40,30,40));

        emps.add(new WarehouseStaff("Charles Theva", "Charles", "pass"));
        emps.add(new SalesStaff("Craig Cooper", "Craig", "pass"));
        emps.add(new Manager("Phil Vines", "Phil", "pass"));

   }
}
