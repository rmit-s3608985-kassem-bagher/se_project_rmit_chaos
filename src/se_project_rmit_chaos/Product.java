package se_project_rmit_chaos;

import java.util.ArrayList;

public class Product {
    private int id;
    private String name;
    private double unitPrice;
    private double stockLevel;
    private double replenishLevel;
    private UnitType type;
    private ArrayList<Discount> discounts;
    
    public Product(int id, String name, double unitPrice, double stockLevel, double replenishLevel, UnitType type,ArrayList<Discount> discounts) {
	updateProductInfo(id, name, unitPrice, stockLevel, replenishLevel, type, discounts);
    }
    
    public static ArrayList<Product> fetchProductsFromServer(){
	// TODO: fetch from server, loop and store in array
	/*
	 *  the caller should keep track of the products
	 *  calling this method may cause referencing problem
	 *  use it wisely 
	 */
	
	ArrayList<Product> prs = new ArrayList<>();
	
	return prs;
    }

    private void updateProductInfo(int id, String name, double unitPrice, double stockLevel, double replenishLevel, UnitType type,ArrayList<Discount> discounts){
	this.id = id;
	this.name = name;
	this.unitPrice = unitPrice;
	this.stockLevel = stockLevel;
	this.replenishLevel = replenishLevel;
	this.type = type;
	this.discounts = discounts;
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
    
    public double getStockLevel(){
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
    
    public boolean editDiscount(Discount dc,double quantity, double percentage){
	// TODO: edit discount on server
	
	dc.setPercentage(percentage);
	dc.setQuantity(quantity);
	return true;
    }
    
    public boolean deleteDiscount(Discount dc){
	// TODO: delete discount on server
	
	discounts.remove(discounts);
	return true;
    }
    
    public ArrayList<Discount> getDiscounts(){
	return discounts;
    }
    
    public boolean addDiscount(double quantity,double percentage){
	// TODO: add new discount on server
	
	// add discount to product locally
	Discount dc = new Discount(percentage, quantity);
	discounts.add(dc);
	return true;
    }
}
