package au.edu.rmit.chaos.menu;

import au.edu.rmit.chaos.Employee;
import au.edu.rmit.chaos.Order;
import au.edu.rmit.chaos.Product;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by kassem on 30/4/17.
 */
public class WarehouseMenu {
    private Employee employee;
    private ArrayList<Product> products;
    private Scanner scan;

    public WarehouseMenu(Employee employee) {
	this.employee = employee;
	scan = new Scanner(System.in);
    }

    private void returnToMainMenu() {
	System.out.println("\n\n\tPress return to continue...");
	scan.nextLine();
    }

    private void checkStockLevel() {

	System.out.print("\n\n\tPlease Wait...\n");

	products = Product.fetchProductsFromServer();
	do {
	    int option;
	    System.out.println("\n\n\t\tAvailable Products");
	    for (int x = 0; x < products.size(); x++) {
		System.out.printf("\t" + "%-40s %d %n", products.get(x).getName(), x);
	    }
	    System.out.printf("\t" + "%-40s %d %n", "Back to main menu", products.size());
	    System.out.println("\n\t" + String.format("%40s", "*").replace(' ', '*'));
	    System.out.print("\tYour choice : ");

	    try {
		option = scan.nextInt();
		scan.nextLine();
	    } catch (Exception e) {
		continue;
	    }

	    // exit selected
	    if (option == products.size()) {
		break;
	    }

	    // unknown selection
	    if (option > products.size()) {
		continue;
	    }

	    Product pr = products.get(option);
	    System.out.printf("%n\tThere are %d %s of %s %n", pr.getStockLevel(),pr.getType().toString(), pr.getName());
	    returnToMainMenu();
	    return;
	} while (true);
    }

    public void display() {
	int option = 0;

	do {
	    System.out.println("\n\n\t\t\tWelcome " + employee.getName());
	    System.out.printf("\t %-50s %-2s %n", "Check Stock Level", "0");
	    System.out.printf("\t %-50s %-2s %n", "Exit", "1");
	    System.out.println(String.format("%n\t%-53s%n", " ").replace(" ", "*"));
	    System.out.print("\tYour choice : ");

	    try {
		option = scan.nextInt();
		scan.nextLine();
	    } catch (Exception e) {

	    }
	    if (option == 0) {
		checkStockLevel();
	    } else if (option == 1) {
		System.exit(0);
	    }

	} while (true);

    }
}
