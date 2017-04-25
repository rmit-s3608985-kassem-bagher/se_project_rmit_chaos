package au.edu.rmit.chaos;

public class Item {
    protected int quantity;
    protected Product product;
    
    public Item(int quantity, Product product) {
	this.quantity = quantity;
	this.product = product;
    }
    
    protected Product getProduct() {
	return product;
    }

    protected double getQuantity() {
	return quantity;
    }
    
    protected double getPrice() {
	double val = (this.product.getProductPrice() * this.quantity);
	return val;
    }

}
