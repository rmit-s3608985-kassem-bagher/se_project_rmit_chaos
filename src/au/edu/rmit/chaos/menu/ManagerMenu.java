package au.edu.rmit.chaos.menu;

import java.util.Scanner;
import au.edu.rmit.chaos.*;

public class ManagerMenu {
    Employee employee;
    Scanner scan;

    public ManagerMenu(Employee employee) {
	this.employee = employee;
	scan = new Scanner(System.in);
    }

    public void display() {
	int option = 0;

	do {
	    System.out.println("\n\n\t\t\tWelcome " + employee.getName());
	    System.out.printf("\t %-50s %-2s %n", "Place Purchase Order", "0");
	    System.out.printf("\t %-50s %-2s %n", "Edit product discount", "1");
	    System.out.printf("\t %-50s %-2s %n", "New Bulk Sale Discount", "2");
	    System.out.printf("\t %-50s %-2s %n", "Edit Bulk Sale Discount", "3");
	    System.out.printf("\t %-50s %-2s %n", "Remove Bulk Sale Discount", "4");
	    System.out.printf("\t %-50s %-2s %n", "Sales Report", "5");
	    System.out.printf("\t %-50s %-2s %n", "Supply Report", "6");
	    System.out.printf("\t %-50s %-2s %n", "Best Selling Report", "7");
	    System.out.printf("\t %-50s %-2s %n", "Exit", "8");
	    System.out.println(String.format("%n\t%-53s%n", " ").replace(" ", "*"));
	    System.out.print("\tYour choice : ");

	    try {
		option = scan.nextInt();
		scan.nextLine();
	    } catch (Exception e) {

	    }
	    if (option == 0) {
		// checkStockLevel();
	    } else if (option == 1) {
//		System.exit(0);
	    }else if (option == 2) {
//		System.exit(0);
	    }else if (option == 3) {
//		System.exit(0);
	    }else if (option == 4) {
//		System.exit(0);
	    }else if (option == 5) {
//		System.exit(0);
	    }else if (option == 6) {
//		System.exit(0);
	    }else if (option == 6) {
//		System.exit(0);
	    }else if (option == 8) {
		System.exit(0);
	    }
	} while (true);
    }
}
