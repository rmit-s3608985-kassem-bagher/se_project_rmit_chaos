package se_project_rmit_chaos;

import java.util.ArrayList;

public class PurchaseOrder {
    private int id;
    private long date;
    private Supplier supplier;
    private ArrayList<PurchaseOrderItem> orderItems = new ArrayList<PurchaseOrderItem>();

    public PurchaseOrder(Supplier supplier) {
	this.supplier = supplier;
    }

    public boolean addProduct(Product pr, int qty) {
	PurchaseOrderItem item = new PurchaseOrderItem(pr, qty);
	this.orderItems.add(item);
	return true;
    }

    public boolean placeOrder() {
	/*
	 * TODO: call API to place order and increase stock level, on success,
	 * complete the below steps
	 */
	for (PurchaseOrderItem oi : orderItems) {
	    oi.getProduct().increaseStockLevel(oi.getQuantity());
	}
	this.date = System.currentTimeMillis() / 1000l;
	this.id = 1; // get from API
	return true;
    }
}
