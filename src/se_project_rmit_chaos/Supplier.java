package se_project_rmit_chaos;

import java.util.ArrayList;

public class Supplier {
    private int id;
    private String name;
    private String address;
    private int postcode;
    private String phone;
    private ArrayList<Product> products;

    public Supplier(int id, String name, String address, int postcode, String phone,ArrayList<Product> products) {
	this.id = id;
	this.name = name;
	this.address = address;
	this.postcode = postcode;
	this.phone = phone;
	this.products = products;
    }
    
    /**
     * <p>Retrieves all suppliers from the server</p>
     * 
     * You will need to keep track of local changes made in the code
     * @return list of suppliers with their products
     */
    
    public static ArrayList<Supplier> fetchSuppliersFromServer(){
	// TODO: fetch from server, loop and store in array
	/*
	 *  the caller should keep track of the suppliers
	 *  calling this method may cause referencing problem
	 *  use it wisely 
	 */
	
	ArrayList<Supplier> sups = new ArrayList<Supplier>();
	
	return sups;
    }
    
    /**
     * <p>Update supplier's information<p>
     * 
     * Only variables with data will be changed in the system.<br>
     * For string data, pass "" to keep as is<br>
     * For numbers, pass -1 to keep as is
     *
     * @param name the name of the 
     * @param address
     * @param postcode
     * @param phone
     * @return
     */
    public boolean editSupplier(String name, String address, int postcode, String phone){
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
     * @return products provided by the supplier
     */
    public ArrayList<Product> getSupplierProducts(){
	return this.products;
    }
    
}
