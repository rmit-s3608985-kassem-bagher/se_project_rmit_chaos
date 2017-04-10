package se_project_rmit_chaos;

public class Customer 
{
   private String name;
   private double rate;

   public Customer(String name, double rate)
   {
        this.name = name;
        this.rate = rate;
   }

   public double getDisVal(double amt)
   {
        double val = amt * rate/100;
        return val;
   }
   public String getName() { return name; }

}

