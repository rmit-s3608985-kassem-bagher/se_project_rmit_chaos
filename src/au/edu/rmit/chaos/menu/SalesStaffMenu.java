package au.edu.rmit.chaos.menu;

import java.util.Scanner;

import au.edu.rmit.chaos.*;

public class SalesStaffMenu {
    private Employee employee;
    private Scanner scan;

    public SalesStaffMenu(Employee emp) {
	this.employee = emp;
	scan = new Scanner(System.in);
    }

    public void cancelOrderMenu() {
	int or;
	System.out.print("\n\n\tPlease enter order number: ");
	try {
	    or = scan.nextInt();
	    scan.nextLine();
	} catch (Exception e) {
	    System.out.print("\tPlease enter a valid number 😒!");
	    return;
	}
	if (Order.cancelOrder(or)) {
	    System.out.print("\n\tOrder canceled successfully 😃");
	} else {
	    System.out.print("\n\tOrder canceled successfully 😃");
	}
	returnToMainMenu();
    }

    private void returnToMainMenu() {
	System.out.println("\n\n\tPress return to go back to main menu...");
	scan.nextLine();
    }

    public void editOrderMenu() {
	int orID;
	int prID;
	System.out.print("\n\n\tPlease enter order number: ");
	try {
	    orID = scan.nextInt();
	    scan.nextLine();
	    Order or = new Order(null);
	    if (!or.getOrderByID(orID)) {
		returnToMainMenu();
		return;
	    }
	    System.out.printf("\n\t" + "%-40s %n", "Edit order #" + or.getID());
	    for (int x = 0; x < or.getOrderItems().size(); x++) {
		OrderItem item = or.getOrderItems().get(x);
		System.out.printf("\t%-40s %-10s %n", item.getProduct().getName(), x);
	    }
	    System.out.print("\n\tPlease enter item number to remove it: ");
	    prID = scan.nextInt();
	    scan.nextLine();
	    if (prID >= or.getOrderItems().size())
		throw new Exception();

	    if (!or.removeProduct(or.getOrderItems().get(prID))) {
		returnToMainMenu();
		return;
	    } else {
		System.out.print("\n\tItem removed successfully 😃");
		returnToMainMenu();
		return;
	    }

	} catch (Exception e) {
	    returnToMainMenu();
	    return;
	}
    }

    public void display() {
	char ch = 0;

	do {
	    System.out.println("\n\n\t\t\tWelcome " + employee.getName());
	    System.out.println("\tCancel Order 					0");
	    System.out.println("\tEdit Order					1");
	    System.out.println("\tTopup Customer's card				3");
	    System.out.println("\tExit						4");
	    System.out.println("\n\t*************************************************");
	    System.out.print("\tYour choice : ");

	    try {
		ch = scan.nextLine().charAt(0);
	    } catch (Exception e) {

	    }
	    if (ch == '0') {
		cancelOrderMenu();
	    } else if (ch == '1') {
		editOrderMenu();
	    } else if (ch == '3') {
		// checkApplicableDiscunts();
	    } else if (ch == '4') {
		System.exit(0);
	    }

	} while (true);

    }
}
