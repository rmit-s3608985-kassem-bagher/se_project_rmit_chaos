package au.edu.rmit.chaos.menu;

import java.util.ArrayList;
import java.util.Scanner;

import com.mashape.unirest.http.exceptions.UnirestException;

import au.edu.rmit.chaos.*;

public class CustomerMenu {
    private Customer customer;
    private Scanner scan;
    private ArrayList<Product> products;
    private Order order;

    public CustomerMenu(Customer cu) {
	this.customer = cu;
	scan = new Scanner(System.in);
    }

    private void printInvoic() {
	System.out.println("\n\n\tOrder Placed Successfully 😃✌️!\n");
	System.out.printf("\t" + "%-40s %-10s %-10s %-10s %n", "Order (#" + order.getID() + ")", "qty", "unit price",
		"total");
	for (OrderItem item : order.getOrderItems()) {
	    System.out.printf("\t" + "%-40s %-10s %-10s %-10s %n", item.getProduct().getName(), item.getQuantity(),
		    item.getProduct().getUnitPrice(), item.getTotal());
	}
	System.out.println("\t--------------------------------------------------------------");
	System.out.printf("\t" + "%-40s %-10s %-10s %-10s %n", "Subtotal", " ", " ", order.getSubtotal()+order.getDiscount());
	System.out.printf("\t" + "%-40s %-10s %-10s %-10s %n", "Discount", " ", " ", order.getPointsDiscount()+order.getDiscount());
	System.out.printf("\t" + "%-40s %-10s %-10s %-10s %n", "Total", " ", " ", order.getTotal());

	System.out.println("\n\tPress return to go back to main menu...");
	scan.nextLine();
    }

    private void loadProducts() {
	products = Product.fetchProductsFromServer();
    }

    private int productQuantity(Product pr) {
	int qty;
	System.out.print("\tEnter quantity for " + pr.getName() + ": ");
	try {
	    qty = scan.nextInt();
	    scan.nextLine();
	} catch (Exception e) {
	    System.out.println("\tPlease enter a valid quantity 😒");
	    return 0;
	}
	return qty;
    }

    private void checkProductPriceMenu() {

    }

    private void placeOrderMenu() {
	char ch;

	// new order
	if (order == null) {
	    System.out.println("\n\tPlease Wait...\n");
	    loadProducts();
	    try {
		order = new Order(customer);
	    } catch (UnirestException e) {
		e.printStackTrace();
	    }
	}

	do {

	    System.out.println("\n\n\t\tAvailable Products");
	    for (int x = 0; x < products.size(); x++) {
		System.out.printf("\t" + "%-40s %d %n", products.get(x).getName(), x);
	    }

	    System.out.println("\n\t" + String.format("%40s", "*").replace(' ', '*'));
	    System.out.print("\tYour choice : ");
	    try {
		ch = scan.nextLine().charAt(0);
	    } catch (Exception e) {
		continue;
	    }
	    // specify quantity
	    int qty = productQuantity(products.get(Character.getNumericValue(ch)));
	    if (qty == 0) {
		continue;
	    }
	    this.order.addProduct(products.get(Character.getNumericValue(ch)), qty);

	    do {
		System.out.print("\tDo you want to add more products? Y/N: ");
		ch = scan.nextLine().toLowerCase().trim().charAt(0);
	    } while (ch != 'y' && ch != 'n');

	    // more products
	    if (ch == 'n') {
		while (!this.order.placeOrder()) {
		    System.out.println("\tReference order #"+order.getID()+" to the sales staff.");
		    System.out.println("\tPlease wait for sales staff assistant 🙁!");
		    scan.nextLine();
		}
		printInvoic();
		break;
	    }
	} while (true);

    }

    public void display() {
	char ch = 0;

	do {
	    System.out.println("\n\n\t\t\tWelcome " + customer.getName());
	    System.out.println("\tPlace Order					0");
	    System.out.println("\tCheck Product Price				1");
	    System.out.println("\tCheck Applicable Discount			3");
	    System.out.println("\tExit						4");
	    System.out.println("\n\t********************************************");
	    System.out.print("\tYour choice : ");

	    try {
		ch = scan.nextLine().charAt(0);
	    } catch (Exception e) {
		
	    }
	    if (ch == '0') {
		placeOrderMenu();
		this.order = null;
	    } else if (ch == '4') {
		System.exit(0);
	    }

	} while (true);

    }
}
