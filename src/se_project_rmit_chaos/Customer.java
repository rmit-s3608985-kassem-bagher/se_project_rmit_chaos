package se_project_rmit_chaos;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

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
	HttpResponse<JsonNode> request = null;
	try {
	    request = Unirest.get(
	    	"http://localhost/supermarket/api.php/customer/login?username=" + username + "&password=" + password)
	    	.header("accept", "application/json").asJson();
	} catch (UnirestException e) {
	    e.printStackTrace();
	    return false;
	}
	// retrieve the parsed JSONObject from the response
	JSONObject json = request.getBody().getObject();
	
	this.name = json.getString("name");
	this.id = json.getInt("id");
	this.balance = json.getDouble("balance");
	this.points = json.getInt("points");
	this.loggedIn = true;

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

    public void increaseBalance(double amount) {
	this.balance += amount;
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
