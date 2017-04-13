package se_project_rmit_chaos;

public class Customer {
    private String name;
    private int id;
    private double balance = 0;
    private int points = 0;
    private boolean loggedIn = false;
    private String username = "";
    private String password = "";

    public Customer(String username, String password) {
	this.username = username;
	this.password = password;
	if (login(username, password)) {
	    System.out.println("Customer Created and logged in");
	} else {
	    System.err.println("Customer Created but could not login!\nYou need to call the login function");
	}

    }

    public boolean login(String username, String password) {
	// TODO: call customer login API and fill the data from the response
	this.name = "";
	this.id = 1;
	this.balance = 0;
	this.points = 0;
	this.loggedIn = false;

	// operation status
	if (this.loggedIn) {
	    return true;
	}
	return false;
    }

    public boolean isLoggedIn() {
	return this.loggedIn;
    }

    public String getName() {
	return name;
    }

    public double getBalance() {
	return balance;
    }
    
    public void increaseBalance(double amount){
	this.balance+= amount;
    }

    public boolean deductBalance(double amount) {
	// TODO: call the deduct balance API
	this.balance -= amount;
	return true;
    }

    public int getPoints() {
	return this.points;
    }

    public double getPointsDiscount(double orderSubtotal) {

	double discountAmount = Math.floor(this.points / 20.0) * 5.0;
	return discountAmount;
    }

    public boolean deductPoints(int points) {
	this.points -= points;
	return true;
    }

    public boolean deductPoints(double subtotal) {
	int points = (int) (Math.floor(this.points / 20.0)) * 20;
	this.points -= points;
	return true;
    }

    public boolean addPoints(int points) {
	// TODO: call the add points API
	this.points += points;
	return true;
    }
    
    public boolean addPoints(double subtotal) {
	int points = (int) (Math.floor(this.points / 20.0)) * 20;
	this.points += points;
	return true;
    }
    

}
