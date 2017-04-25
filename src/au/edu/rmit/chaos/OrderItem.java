package au.edu.rmit.chaos;

public class OrderItem extends Item {
    private double total;

    public OrderItem(Product p, int qty) {
	super(qty, p);
    }

    @Override
    public double getPrice() {
	double val = super.getPrice() - getBulkPrice();
	this.total = val;
	return val;
    }

    private double getBulkPrice() {
	double bulkPrice = 0;
	for (Discount disc : this.product.getDiscounts()) {
	    if (quantity >= disc.getQuantity()) { // discount applies
		double tmp = (this.product.getUnitPrice() * disc.getQuantity()) * (disc.getPercentage() / 100);
		System.out.println(tmp);
		bulkPrice = bulkPrice > tmp ? bulkPrice : tmp;
	    }
	}
	return bulkPrice;
    }
}
