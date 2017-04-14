package se_project_rmit_chaos;

import java.util.*;

class Order {
    private int id;
    private long date;
    private double total; // after applying discount
    private double subtotal; // before discount
    private double pointsDiscount; // customer's points discount
    private Customer customer;
    private ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
    private OrderStatus status = OrderStatus.pending;

    public Order(Customer customer) {
	this.customer = customer;
	/*
	 *  call API and place order
	 *  get order id from API
	 */
	this.id = 1; // get from server
    }

    public int getID() {
	return id;
    }

    public long getDate() {
	return date;
    }

    private void computeTotal() {
	this.subtotal = 0;
	// calculate subtotal
	for (int i = 0; i < this.orderItems.size(); i++)
	    this.subtotal += this.orderItems.get(i).getPrice();
	// calculate points discount
	this.pointsDiscount = this.customer.getPointsDiscount(this.subtotal);
	// calculate total
	this.total = this.subtotal - this.pointsDiscount;
    }

    public boolean addProduct(Product pr, int qty) {
	if (this.status == OrderStatus.placed) {
	    System.err.println("Cannot add products to already placed orders");
	    return false;
	}
	if (this.status == OrderStatus.canceled) {
	    System.err.println("Cannot add products to a canceled order");
	    return false;
	}
	if (qty > pr.getStockLevel()) {
	    System.err.println("only " + pr.getStockLevel() + " products remaining");
	    return false;
	}
	OrderItem item = new OrderItem(pr, qty, this);
	this.orderItems.add(item);
	return true;
    }

    public boolean removeProduct(OrderItem item) {
	if (this.status == OrderStatus.canceled) {
	    System.err.println("Cannot remove products to a canceled order");
	    return false;
	}
	this.orderItems.remove(item);
	return true;
    }

    public boolean cancelOrder() {
	if (this.status == OrderStatus.canceled) {
	    System.err.println("order canceled already ");
	    return false;
	}
	if (this.status == OrderStatus.pending) {
	    System.err.println("cannot cancel pending orders.\nOrder must be placed first");
	    return false;
	}
	// TODO: call the API and complete on success (server will do similar
	// logic)
	if (this.status == OrderStatus.pending) { // no deduction or anything
	    this.status = OrderStatus.canceled;
	    return true;
	} else if (this.status == OrderStatus.placed) {
	    this.status = OrderStatus.canceled;
	    this.customer.increaseBalance(this.total);
	    this.customer.addPoints(this.subtotal);
	    for (OrderItem oi : orderItems) {
		oi.getProduct().increaseStockLevel(oi.getQuantity());
	    }
	    return true;
	}

	return false;
    }

    public boolean placeOrder() {
	/*
	 * TODO: call API (first check with server for stock level) add items to
	 * order update total deduct user's balance deduct user's points
	 * decrease stock level
	 * if equals or below replenish level, place order automatically
	 * App should sync data everytime user goes back to menu 
	 */
	if (this.status != OrderStatus.pending) {
	    System.err.println("order needs to be pending");
	    return false;
	}
	computeTotal();
	// check if customer has enough balance
	if (this.total > this.customer.getBalance() && this.total > this.customer.getPointsDiscount(this.subtotal)) {
	    System.err.println("no enough balance or points");
	    return false;
	}

	// TODO: call API to place order, on success, complete the below steps
	this.customer.deductBalance(this.total);
	this.customer.deductPoints(subtotal);
	for (OrderItem oi : orderItems) {
	    oi.getProduct().decreaseStockLevel(oi.getQuantity());
	}
	this.date = System.currentTimeMillis() / 1000l;
	this.status = OrderStatus.placed;
	return true;
    }

}