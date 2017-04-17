package se_project_rmit_chaos;

import java.util.ArrayList;

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

	if (jsonArray == null) // no data
	    return null;

	if (jsonArray.getJSONObject(0).has("error")) {
	    System.err.println(jsonArray.getJSONObject(0).getJSONObject("error").getString("message"));
	    return null;
	}
	ArrayList<Supplier> sups = new ArrayList<Supplier>();
	for (int i = 0; i < jsonArray.length(); i++) {
	    JSONObject json = jsonArray.getJSONObject(i);
	    Supplier sup = new Supplier(json.getInt("id"), json.getString("name"), json.getString("address"),
		    json.getInt("postcode"), json.getString("phone"), null);
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

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
