package se_project_rmit_chaos;

public class OrderItem {
    private double quantity;
    private Product product;
    private Order order;
    private double total;

    public OrderItem(Product p, double qty, Order order) {
	this.product = p;
	this.quantity = qty;
	this.order = order;
    }

    public Order getOrder() {
	return order;
    }

    public Product getProduct() {
	return product;
    }

    public double getQuantity() {
	return quantity;
    }

    public double getPrice() {
	double val = (this.product.getProductPrice() * this.quantity) - getBulkPrice();
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
