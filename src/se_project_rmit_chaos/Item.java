package se_project_rmit_chaos;

public class Item {
    protected double quantity;
    protected Product product;
    
    public Item(double quantity, Product product) {
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
