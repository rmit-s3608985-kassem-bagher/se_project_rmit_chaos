package au.edu.rmit.chaos.menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import au.edu.rmit.chaos.*;

public class ManagerMenu extends Menu {
    private Employee employee;
    private ArrayList<Supplier> suppliers;
    private Scanner scan;
    private ArrayList<Product> products;

    public ManagerMenu(Employee employee) {
	this.employee = employee;
	scan = new Scanner(System.in);
    }

    private void loadProducts() {
	this.products = Product.fetchProductsFromServer();
    }

    private void loadSuppliers() {
	suppliers = Supplier.fetchSuppliersFromServer();
    }

    private void removeBulkSale(){
	loadProducts();
	Discount disc = displayDiscountInput(products);
	for (Product product : products) {
	    if (product.getDiscounts().contains(disc)){
		if(!displayYesOrNoMessage("Are you sure you wante to remove the discount"))
		    return;
		if (!product.deleteDiscount(disc)) {
		    pressToContinue();
		    return;
		}
		System.out.println("\n\tDiscount removed successfully 👍!");
		pressToContinue();
		return;
	    }
	}
    }
    
    private void editBulkSale(){
	loadProducts();
	Discount disc = displayDiscountInput(products);
	Product pr=null;
	for (Product product : products) {
	    if (product.getDiscounts().contains(disc)){
		pr = product;
		break;
	    }
	}
	int qty = displayIntegerInputMessage("Enter new quantity for " + pr.getName());
	if (qty < 0) {
	    invalidOptionMessage();
	    pressToContinue();
	    return;
	}
	int perc = displayIntegerInputMessage("Enter new discount percentage for " + pr.getName());
	if (perc < 0) {
	    invalidOptionMessage();
	    pressToContinue();
	    return;
	}
	if (!pr.editDiscount(disc, qty, perc)) {
	    pressToContinue();
	    return;
	}
	System.out.println("\n\tDiscount edited successfully 👍!");
	pressToContinue();
	return;
    }
    
    private void newBulkSale() {
	loadProducts();
	int prID = displayProductsInput(products, "Available Products", true);
	if (prID == products.size()) {
	    return;
	} else if (prID == -1) {
	    invalidOptionMessage();
	    pressToContinue();
	    return;
	}
	Product pr = products.get(prID);
	int qty = displayIntegerInputMessage("Enter quantity for " + pr.getName());
	if (qty < 0) {
	    invalidOptionMessage();
	    pressToContinue();
	    return;
	}
	int perc = displayIntegerInputMessage("Enter discount percentage for " + pr.getName());
	if (perc < 0) {
	    invalidOptionMessage();
	    pressToContinue();
	    return;
	}
	if (!pr.addDiscount(qty, perc)) {
	    pressToContinue();
	    return;
	}
	System.out.println("\n\tDiscount added successfully 👍!");
	pressToContinue();
	return;
    }

    private void placePurchaseOrder() {
	int option;
	do {
	    loadSuppliers();
	    option = displaySuppliersInput(suppliers, "Available Suppliers", true);
	    if (option == suppliers.size()) {
		break;
	    } else if (option < 0) {
		invalidOptionMessage();
		pressToContinue();
		return;
	    }

	    Supplier sup = suppliers.get(option);
	    PurchaseOrder po = new PurchaseOrder(sup, employee);
	    do {
		int prID = displayProductsInput(sup.getSupplierProducts(), "Select a product from " + sup.getName(),
			false);
		if (prID < 0) {
		    continue;
		}
		Product pr = sup.getSupplierProducts().get(prID);
		int qty = displayIntegerInputMessage("Enter quantity for " + pr.getName());
		if (qty < 0)
		    continue;
		po.addProduct(pr, qty);

		if (!displayYesOrNoMessage("Do you want to add more products?")) {
		    while (!po.placeOrder()) {
			pressToContinue();
			continue;
		    }
		    System.out.println("\n\n\tPurchase order placed successfully 👍!");
		    pressToContinue();
		    return;
		}
	    } while (true);
	} while (true);

    }

    private void editProductPrice() {
	int option;
	do {
	    loadProducts();
	    option = displayProductsInput(products, "Available Products", true);
	    if (option == products.size()) {
		break;
	    } else if (option < 0) {
		invalidOptionMessage();
		pressToContinue();
		return;
	    }
	    Product pr = products.get(option);
	    String message = pr.getName() + " old price is " + pr.getProductPrice() + ", enter new price";
	    double newPrice = displayDoubleInputMessage(message);
	    if (!pr.editUnitPrice(newPrice))
		break;
	    System.out.println("\tPrice updated successfully 👍!");
	    pressToContinue();
	    return;

	} while (true);
    }

    public void display() {
	int option = 0;
	do {
	    System.out.println("\n\n\t\t\tWelcome " + employee.getName());
	    System.out.printf("\t %-50s %-2s %n", "Place purchase prder", "0");
	    System.out.printf("\t %-50s %-2s %n", "Edit product price (promotion)", "1");
	    System.out.printf("\t %-50s %-2s %n", "New bulk sale discount", "2");
	    System.out.printf("\t %-50s %-2s %n", "Edit bulk sale discount", "3");
	    System.out.printf("\t %-50s %-2s %n", "Remove bulk sale discount", "4");
	    System.out.printf("\t %-50s %-2s %n", "Sales report", "5");
	    System.out.printf("\t %-50s %-2s %n", "Supply report", "6");
	    System.out.printf("\t %-50s %-2s %n", "Best selling report", "7");
	    System.out.printf("\t %-50s %-2s %n", "Exit", "8");
	    System.out.println(String.format("%n\t%-53s%n", " ").replace(" ", "*"));
	    System.out.print("\tYour choice : ");

	    try {
		option = scan.nextInt();
		scan.nextLine();
	    } catch (Exception e) {

	    }
	    if (option == 0) {
		placePurchaseOrder();
	    } else if (option == 1) {
		editProductPrice();
	    } else if (option == 2) {
		 newBulkSale();
	    } else if (option == 3) {
		 editBulkSale();
	    } else if (option == 4) {
		 removeBulkSale();
	    } else if (option == 5) {
		// System.exit(0);
	    } else if (option == 6) {
		// System.exit(0);
	    } else if (option == 6) {
		// System.exit(0);
	    } else if (option == 8) {
		System.exit(0);
	    }
	} while (true);
    }
}
