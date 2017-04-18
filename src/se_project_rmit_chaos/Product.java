package se_project_rmit_chaos;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Product {
    private int id;
    private String name;
    private double unitPrice;
    private int stockLevel;
    private double replenishLevel;
    private UnitType type;
    private ArrayList<Discount> discounts = new ArrayList<Discount>();
    
    public Product(int id, String name, double unitPrice, int stockLevel, double replenishLevel, UnitType type,ArrayList<Discount> discounts) {
	updateProductInfo(id, name, unitPrice, stockLevel, replenishLevel, type, discounts);
    }
    
    public static ArrayList<Product> fetchProductsFromServer(){
	HttpResponse<JsonNode> request = null;
	try {
	    request = Unirest
		    .get("http://localhost/supermarket/api.php/product/")
		    .header("accept", "application/json")
		    .queryString("key", "519428fdced64894bb10cd90bd87167c")
		    .asJson();
	} catch (UnirestException e) {
	    e.printStackTrace();
	    return null;
	}
	// retrieve the parsed JSONObject from the response
	if (!request.getBody().isArray()) {
	    JSONObject json = request.getBody().getObject();
	    if(json.has("error"))
		System.err.println(json.getJSONObject("error").getString("message"));
	    return null;
	}	
	return JsonParser.parseProducts(request.getBody().getArray());
    }

    private void updateProductInfo(int id, String name, double unitPrice, int stockLevel, double replenishLevel, UnitType type,ArrayList<Discount> discounts){
	this.id = id;
	this.name = name;
	this.unitPrice = unitPrice;
	this.stockLevel = stockLevel;
	this.replenishLevel = replenishLevel;
	this.type = type;
	this.discounts = discounts;
    }
    
    public UnitType getType(){
	return this.type;
    }
    
    public double getProductPrice(){
	return this.unitPrice;
    }
    
    public boolean editUnitPrice(double newPrice) {
	// TODO: call edit product price API
	this.unitPrice = newPrice;
	return true;
    }

    public ArrayList<Product> getAllProducts() {
	return fetchProductsFromServer();
    }
    
    public int getStockLevel(){
	return stockLevel;
    }
    public boolean setStockLevel(double newLevel){
	// TODO: call the update stock level on the server
	
	this.replenishLevel = newLevel;
	return true;
    }
    
    public double increaseStockLevel(double quantity){
	// TODO: call the update stock level on the server
	
	this.stockLevel += quantity;
	return this.stockLevel ;
    }
    
    public double decreaseStockLevel(double quantity){
	// TODO: call the update stock level on the server
	this.stockLevel -= quantity;
	return this.stockLevel ;
    }
    

    public double getUnitPrice() {
	return this.unitPrice;
    }

    public int getID() {
	return this.id;
    }

    public String getName() {
	return this.name;
    }

    public double getReplenishLevel() {
	return replenishLevel;
    }
    public boolean setReplenishhLevel(double newLevel){
	// TODO: call the update replenish level on the server
	
	this.replenishLevel = newLevel;
	return true;
    }
    
    public boolean editDiscount(Discount dc,int quantity, double percentage){
	HttpResponse<JsonNode> request = null;
	try {
	    request = Unirest
		    .post("http://localhost/supermarket/api.php/product/{id}/update_discount")
		    .header("accept", "application/json")
		    .header("Content-Type", "application/json")
		    .routeParam("id",Integer.toString(this.id))
		    .queryString("key", "519428fdced64894bb10cd90bd87167c")
		    .queryString("old_percentage", dc.getPercentage())
		    .queryString("new_percentage", percentage)
		    .queryString("old_quantity", dc.getQuantity())
		    .queryString("new_quantity", quantity)
		    .asJson();
	} catch (UnirestException e) {
	    e.printStackTrace();
	    return false;
	}
	// retrieve the parsed JSONObject from the response
	JSONObject json = request.getBody().getObject();
	if (json.has("error")) {
	    System.err.println(json.getJSONObject("error").getString("message"));
	    return false;
	}
	if(json.getJSONObject("discount")==null){
	    System.err.println("could not edit discount");
	    return false;
	}
	json = json.getJSONObject("discount");
	dc.setPercentage(json.getInt("percentage"));
	dc.setQuantity(json.getInt("quantity"));
	return true;
    }
    
    public boolean deleteDiscount(Discount dc){
	HttpResponse<JsonNode> request = null;
	try {
	    request = Unirest
		    .post("http://localhost/supermarket/api.php/product/{id}/delete_discount")
		    .header("accept", "application/json")
		    .header("Content-Type", "application/json")
		    .routeParam("id",Integer.toString(this.id))
		    .queryString("key", "519428fdced64894bb10cd90bd87167c")
		    .queryString("percentage", dc.getPercentage())
		    .queryString("quantity", dc.getQuantity())
		    .asJson();
	} catch (UnirestException e) {
	    e.printStackTrace();
	    return false;
	}
	// retrieve the parsed JSONObject from the response
	JSONObject json = request.getBody().getObject();
	if (json.has("error")) {
	    System.err.println(json.getJSONObject("error").getString("message"));
	    return false;
	}
	if(json.getJSONObject("responce")==null){
	    System.err.println("could not delete discount");
	    return false;
	}
	discounts.remove(discounts);
	return true;
    }
    
    public ArrayList<Discount> getDiscounts(){
	return discounts;
    }
    
    public boolean addDiscount(int quantity,int percentage){
	HttpResponse<JsonNode> request = null;
	try {
	    request = Unirest
		    .post("http://localhost/supermarket/api.php/product/{id}/add_discount")
		    .header("accept", "application/json")
		    .header("Content-Type", "application/json")
		    .routeParam("id",Integer.toString(this.id))
		    .queryString("key", "519428fdced64894bb10cd90bd87167c")
		    .queryString("percentage", percentage)
		    .queryString("quantity", quantity)
		    .asJson();
	} catch (UnirestException e) {
	    e.printStackTrace();
	    return false;
	}
	// retrieve the parsed JSONObject from the response
	JSONObject json = request.getBody().getObject();
	if (json.has("error")) {
	    System.err.println(json.getJSONObject("error").getString("message"));
	    return false;
	}
	if(json.getJSONObject("responce")==null){
	    System.err.println("could not add discount");
	    return false;
	}

	// add discount to product locally
	Discount dc = new Discount(percentage, quantity);
	discounts.add(dc);
	return true;
    }
}
