package se_project_rmit_chaos;

import java.util.ArrayList;

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
	// TODO: fetch from server, loop and store in array
	/*
	 *  the caller should keep track of the products
	 *  calling this method may cause referencing problem
	 *  use it wisely 
	 */
	
	// TODO: remove dummy data
	ArrayList<Product> prs = new ArrayList<Product>();
	prs.add(new Product(1, "Mango", 2.0, 20, 10, UnitType.kg, new ArrayList<Discount>()));
	prs.add(new Product(1, "Carrot", 4.5, 10, 10, UnitType.kg, new ArrayList<Discount>()));
	prs.add(new Product(1, "Water", 1.5, 5, 20, UnitType.pcs, new ArrayList<Discount>()));
	prs.add(new Product(1, "Pepsi", 3.5, 20, 10, UnitType.pcs, new ArrayList<Discount>()));
	
	return prs;
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
    
    public boolean addDiscount(int quantity,int percentage){
	// TODO: add new discount on server
	
	// add discount to product locally
	Discount dc = new Discount(percentage, quantity);
	discounts.add(dc);
	return true;
    }
}
