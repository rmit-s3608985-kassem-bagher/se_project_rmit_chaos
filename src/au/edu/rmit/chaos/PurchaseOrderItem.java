package au.edu.rmit.chaos;

public class PurchaseOrderItem extends Item {

    private double total;
    
    public PurchaseOrderItem(Product p, int qty) {
	super(qty, p);
	getPrice();
    }
    
    @Override
    public double getPrice() {
	this.total= super.getPrice();
        return this.total;
    }
    
}
