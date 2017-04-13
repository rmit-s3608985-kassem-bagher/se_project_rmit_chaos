package se_project_rmit_chaos;

public class Customer {
    private String name;
    private int id;
    private double balance;
    private int points;
    private boolean loggedIn;
    private String username;
    private String password;

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

    public String getBalance() {
	return name;
    }

    public int getPoints() {
	return this.points;
    }

    public boolean deductPoints(int points) {
	// TODO: call the deduct points API
	return true;
    }

    public boolean addPoints(int points) {
	// TODO: call the add points API
		return true;
    }

}
