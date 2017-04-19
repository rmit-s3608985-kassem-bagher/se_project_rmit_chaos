package se_project_rmit_chaos;

import java.util.*;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

class Order {
    private int id = 0;
    private long date = 0;
    private double total = 0; // after applying discount
    private double subtotal = 0; // before discount
    private double pointsDiscount = 0; // customer's points discount
    private Customer customer = null;
    private ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
    private OrderStatus status = OrderStatus.pending;

    public Order(Customer customer) throws UnirestException {
	this.customer = customer;
	HttpResponse<JsonNode> request = null;
	request = Unirest.post("http://localhost/supermarket/api.php/order/add").header("accept", "application/json")
		.header("Content-Type", "application/json").queryString("key", "519428fdced64894bb10cd90bd87167c")
		.queryString("customer_id", customer.getId()).asJson();
	JSONObject json = request.getBody().getObject();
	if (json.has("error")) {
	    System.err.println(json.getJSONObject("error").getString("message"));
	}
	this.id = json.getJSONObject("responce").getInt("order_id");
    }

    public int getID() {
	return id;
    }

    public long getDate() {
	return date;
    }

    /**
     * @deprecated not used anuymore
     */
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

	HttpResponse<JsonNode> request = null;
	try {
	    request = Unirest.post("http://localhost/supermarket/api.php/order/{id}/add_item")
		    .header("accept", "application/json").routeParam("id", Integer.toString(this.id))
		    .header("Content-Type", "application/json").queryString("key", "519428fdced64894bb10cd90bd87167c")
		    .queryString("product_id", pr.getID()).queryString("quantity", qty).asJson();
	} catch (UnirestException e) {
	    e.printStackTrace();
	    return false;
	}
	// retrieve the parsed JSONObject from the response
	JSONObject json = request.getBody().getObject();
	if (json.has("error")) {
	    System.err.println(json.getJSONObject("error").getString("message"));
	    return false;
	}

	// TODO: update item info if exists (parse response from API)
	OrderItem item = new OrderItem(pr, qty);
	this.orderItems.add(item);
	return true;
    }

    public boolean removeProduct(OrderItem item) {
	HttpResponse<JsonNode> request = null;
	try {
		request = Unirest
			.post("http://localhost/supermarket/api.php/order/{id}/remove_item")
			.header("accept", "application/json")
			.header("Content-Type", "application/json")
			.routeParam("id", Integer.toString(this.getID()))
			.queryString("key", "519428fdced64894bb10cd90bd87167c")
			.queryString("product_id", Integer.toString(item.product.getID()))
			.asJson();
	} catch (UnirestException e) {
		e.printStackTrace();
		return false;
	}
	// retrieve the parsed JSONObject from the response
	JSONObject json = request.getBody().getObject();
	if (json.has("error")) {
		System.err.println(json.getJSONObject("error").getString("message"));
		return false;
	}
	return true;
    }

    public boolean cancelOrder() {
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
	HttpResponse<JsonNode> request = null;
	try {
	    request = Unirest.post("http://localhost/supermarket/api.php/order/{id}/place")
		    .header("accept", "application/json")
		    .routeParam("id", Integer.toString(this.getID()))
		    .header("Content-Type", "application/json")
		    .queryString("key", "519428fdced64894bb10cd90bd87167c")
		    .queryString("customer_id",this.customer.getId())
		    .asJson();
	} catch (UnirestException e) {
	    e.printStackTrace();
	    return false;
	}
	// retrieve the parsed JSONObject from the response
	JSONObject json = request.getBody().getObject();
	if (json.has("error")) {
	    System.err.println(json.getJSONObject("error").getString("message"));
	    return false;
	}
	return true;
    }

}