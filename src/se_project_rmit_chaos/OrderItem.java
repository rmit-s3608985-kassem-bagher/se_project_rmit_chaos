package se_project_rmit_chaos;

public class OrderItem extends Item {
    private Order order;
    private double total;

    public OrderItem(Product p, double qty, Order order) {
	super(qty, p);
	this.order = order;
    }

    public Order getOrder() {
	return order;
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
		bulkPrice = bulkPrice > tmp ? bulkPrice : tmp;
	    }
	}
	return bulkPrice;
    }
}
