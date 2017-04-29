package au.edu.rmit.chaos.menu;

import java.util.InputMismatchException;
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

    public void topUpCustomer() {
        String customerUser;
        System.out.print("\n\n\tPlease enter customer's username: ");
        try {
            customerUser = scan.nextLine().trim();
            Customer cu = Customer.searchByUsername(customerUser);

            if (cu == null) {
                returnToMainMenu();
                return;
            }
            System.out.print("\n\n\tEnter the amount to topup "+cu.getName()+"'s card: ");
            double amount = scan.nextDouble();
            scan.nextLine();

            if(amount==0) throw new InputMismatchException();

            if(!cu.increaseBalance(amount)){
                returnToMainMenu();
                return;
            }
            System.out.print("\t"+cu.getName()+"'s card has being topped up successfully\n\tNew Balance is "+cu.getBalance());
            returnToMainMenu();
            return;
        }
        catch (InputMismatchException e){
            System.out.print("\tPlease enter a valid number 😒!\n\n");
            returnToMainMenu();
            return;
        }
        catch (Exception e) {
            returnToMainMenu();
            return;
        }
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
        int option = 0;

        do {
            
	    System.out.println("\n\n\t\t\tWelcome " + employee.getName());
	    System.out.printf("\t %-50s %-2s %n", "Cancel Order", "0");
	    System.out.printf("\t %-50s %-2s %n", "Edit Order", "1");
	    System.out.printf("\t %-50s %-2s %n", "Topup Customer's card", "2");
	    System.out.printf("\t %-50s %-2s %n", "Exit", "3");
	    System.out.println(String.format("%n\t%-53s%n", " ").replace(" ", "*"));
            System.out.print("\tYour choice : ");

            try {
                option = scan.nextInt();
                scan.nextLine();
            } catch (Exception e) {

            }
            if (option == 0) {
                cancelOrderMenu();
            } else if (option == 1) {
                editOrderMenu();
            } else if (option == 2) {
                topUpCustomer();
            } else if (option == 3) {
                System.exit(0);
            }

        } while (true);

    }
}
