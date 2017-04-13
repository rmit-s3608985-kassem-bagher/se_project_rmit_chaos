package se_project_rmit_chaos;

public class Customer {
    private String name;
    private String id;
    private String balance;
    private String points;
    private double rate;

    public Customer(String name, double rate) {
	this.name = name;
	this.rate = rate;
    }
    
    

    public double getDisVal(double amt) {
	double val = amt * rate / 100;
	return val;
    }

    public String getName() {
	return name;
    }

    public String getBalance() {
	return name;
    }

    public String getPoints() {
	return name;
    }

    public String deductPoints(int points) {
	return name;
    }

    public String addPoints(int points) {
	return name;
    }

}
