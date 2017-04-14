package se_project_rmit_chaos;

public class PurchaseOrderItem extends Item {

    private double total;
    
    public PurchaseOrderItem(Product p, int qty) {
	super(qty, p);
    }
    
    @Override
    public double getPrice() {
	this.total= super.getPrice();
        return this.total;
    }
    
}
