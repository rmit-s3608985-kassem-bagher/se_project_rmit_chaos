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

    private void printInvoic(OrderStatus status) {
	if(status == OrderStatus.placed || status == OrderStatus.already_placed)
	    System.out.println("\n\n\tOrder Placed Successfully 😃✌️!\n");
	else if (status == OrderStatus.canceled || status == OrderStatus.already_canceled) {
	    System.out.println("\n\n\tOrder has been canceled️!\n");
	    System.out.println("\n\n\tPress return to go back to main menu...");
	    scan.nextLine();
	    return;
	}
	
	System.out.printf("\t" + "%-40s %-10s %-10s %-10s %n", "Order (#" + order.getID() + ")", "qty", "unit price",
		"total");
	for (OrderItem item : order.getOrderItems()) {
	    System.out.printf("\t" + "%-40s %-10s $%-10s $%-10s %n", item.getProduct().getName(), item.getQuantity(),
		    item.getProduct().getUnitPrice(), item.getTotal());
	}
	System.out.println("\t--------------------------------------------------------------");
	System.out.printf("\t" + "%-40s %-10s %-10s $%-10s %n", "Subtotal", " ", " ",
		order.getSubtotal() + order.getDiscount());
	System.out.printf("\t" + "%-40s %-10s %-10s $%-10s %n", "Discount", " ", " ",
		order.getPointsDiscount() + order.getDiscount());
	System.out.printf("\t" + "%-40s %-10s %-10s $%-10s %n", "Total", " ", " ", order.getTotal());

	System.out.println("\n\n\tPress return to go back to main menu...");
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

    private void checkApplicableDiscunts() {
	products = Product.fetchProductsFromServer();
	boolean discountsAvailable = false;

	System.out.println("\n\n\t\t\tAvailable Discounts");
	System.out.printf("\t" + "%-30s %-10s %s %n", "Product", "qty", "percentage");

	// loop through products
	for (int x = 0; x < products.size(); x++) {
	    // loop through discounts
	    Product pr = products.get(x);
	    for (Discount disc : pr.getDiscounts()) {
		System.out.printf("\t" + "%-30s %-10s %s%% %n", pr.getName(), disc.getQuantity(), disc.getPercentage());
		discountsAvailable = true;
	    }
	}
	if(!discountsAvailable){
	    System.out.println("\n\t\tNo Available Disounts\n");
	}
	
	System.out.println("\n\tPress return to go back...");
	scan.nextLine();
    }

    private void checkProductPriceMenu() {
	products = Product.fetchProductsFromServer();
	do {
	    char ch;
	    System.out.println("\n\n\t\tAvailable Products");
	    for (int x = 0; x < products.size(); x++) {
		System.out.printf("\t" + "%-40s %d %n", products.get(x).getName(), x);
	    }
	    System.out.printf("\t" + "%-40s %d %n", "Back to main menu", products.size());
	    System.out.println("\n\t" + String.format("%40s", "*").replace(' ', '*'));
	    System.out.print("\tYour choice : ");

	    try {
		ch = scan.nextLine().charAt(0);
	    } catch (Exception e) {
		continue;
	    }

	    // exit selected
	    if (ch == Integer.toString(products.size()).charAt(0)) {
		break;
	    }

	    // unknown selection
	    if (Character.getNumericValue(ch) > products.size()) {
		continue;
	    }
	    Product pr = products.get(Character.getNumericValue(ch));
	    System.out.printf("%n\t1 %s of %s costs $%.2f", pr.getType().toString(), pr.getName(), pr.getUnitPrice());
	    System.out.println("\n\tPress return to go back...");
	    scan.nextLine();
	} while (true);
    }

    private void placeOrderMenu() {
	char ch;

	// new order
	if (order == null) {
	    System.out.println("\n\tPlease Wait...\n");
	    loadProducts();
	    try {
		order = new Order(customer);
		order.createNewOrder();
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
	    int item;
	    try {
		item = scan.nextInt();
		scan.nextLine();
	    } catch (Exception e) {
		continue;
	    }
	    // specify quantity
	    int qty = productQuantity(products.get(item));
	    if (qty == 0) {
		continue;
	    }
	    this.order.addProduct(products.get(item), qty);

	    do {
		System.out.print("\tDo you want to add more products? Y/N: ");
		ch = scan.nextLine().toLowerCase().trim().charAt(0);
	    } while (ch != 'y' && ch != 'n');

	    // more products
	    if (ch == 'n') {
		OrderStatus status = this.order.placeOrder(); 
		while (status == OrderStatus.pending || status == OrderStatus.insufficient_balance
			|| status == OrderStatus.unknown) {
		    System.out.println("\n\tInsufficient balance");
		    System.out.println("\tReference order #" + order.getID() + " to the sales staff.");
		    System.out.println("\tPlease wait for sales staff assistant 🙁!");
		    scan.nextLine();
		    status = this.order.placeOrder();
		}
		printInvoic(status);
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
	    } else if (ch == '1') {
		checkProductPriceMenu();
	    } else if (ch == '3') {
		checkApplicableDiscunts();
	    } else if (ch == '4') {
		System.exit(0);
	    }

	} while (true);
    }
}
