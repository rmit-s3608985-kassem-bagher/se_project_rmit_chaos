package se_project_rmit_chaos;

import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Supplier {

    private int id;
    private String name;
    private String address;
    private int postcode;
    private String phone;
    private ArrayList<Product> products;

    public Supplier(int id, String name, String address, int postcode, String phone, ArrayList<Product> products) {
	this.id = id;
	this.name = name;
	this.address = address;
	this.postcode = postcode;
	this.phone = phone;
	this.products = products;
    }

    /**
     * <p>
     * Retrieves all suppliers from the server
     * </p>
     * 
     * You will need to keep track of local changes made in the code
     * 
     * @return list of suppliers with their products
     */

    public static ArrayList<Supplier> fetchSuppliersFromServer() {
	// TODO: fetch from server, loop and store in array
	/*
	 * the caller should keep track of the suppliers calling this method may
	 * cause referencing problem use it wisely
	 */

	HttpResponse<JsonNode> request = null;
	try {
	    request = Unirest.get("http://localhost/supermarket/api.php/supplier").header("accept", "application/json")
		    .asJson();
	} catch (UnirestException e) {
	    e.printStackTrace();
	    return null;
	}
	// retrieve the parsed JSONObject from the response
	JSONArray jsonArray = request.getBody().getArray();

	// no data
	if (jsonArray == null)
	    return null;

	// error response
	if (jsonArray.getJSONObject(0).has("error")) {
	    System.err.println(jsonArray.getJSONObject(0).getJSONObject("error").getString("message"));
	    return null;
	}

	// retrieve suppliers
	ArrayList<Supplier> sups = new ArrayList<Supplier>();
	for (int i = 0; i < jsonArray.length(); i++) {
	    JSONObject jsonSup = jsonArray.getJSONObject(i);
	    // supplier's products
	    ArrayList<Product> products = new ArrayList<Product>();
	    JSONArray jsonProducts = jsonSup.getJSONArray("products");
	    // retrieve supplier's product
	    for (int j = 0; j < jsonProducts.length(); j++) {
		JSONObject product = jsonProducts.getJSONObject(j);
		// product's discounts
		ArrayList<Discount> discounts = new ArrayList<Discount>();
		JSONArray jsonDiscounts = product.getJSONArray("discounts");
		// retrieve product's discounts
		for (int k = 0; k < jsonDiscounts.length(); k++) {
		    JSONObject disc = jsonDiscounts.getJSONObject(k);
		    discounts.add(new Discount(disc.getInt("percentage"), disc.getInt("quantity")));
		}
		products.add(new Product(product.getInt("id"), product.getString("name"),
			product.getDouble("unit_price"), product.getInt("stock_level"),
			product.getInt("replenish_level"), UnitType.valueOf(product.getString("type")), discounts));
	    }

	    Supplier sup = new Supplier(jsonSup.getInt("id"), jsonSup.getString("name"), jsonSup.getString("address"),
		    jsonSup.getInt("postcode"), jsonSup.getString("phone"), products);
	    sups.add(sup);
	}

	return sups;
    }

    /**
     * <p>
     * Update supplier's information
     * <p>
     * 
     * Only variables with data will be changed in the system.<br>
     * For string data, pass "" to keep as is<br>
     * For numbers, pass -1 to keep as is
     *
     * @param name
     *            the name of the
     * @param address
     * @param postcode
     * @param phone
     * @return
     */
    public boolean editSupplier(String name, String address, int postcode, String phone) {
	// TODO: call the edit supplier API, on success complete the code

	this.name = name.isEmpty() ? this.name : name;
	this.address = address.isEmpty() ? this.address : address;
	this.postcode = postcode < 0 ? this.postcode : postcode;
	this.phone = phone.isEmpty() ? this.phone : phone;
	return true;
    }

    public ArrayList<Supplier> getAllSuppliers() {
	return fetchSuppliersFromServer();
    }

    /**
     * Get all products provided by the supplier
     * 
     * @return products provided by the supplier
     */
    public ArrayList<Product> getSupplierProducts() {
	return this.products;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public String getAddress() {
	return address;
    }

    public int getPostcode() {
	return postcode;
    }

    public String getPhone() {
	return phone;
    }

}
