package se_project_rmit_chaos;

public class Employee {
    private String name = "";
    private int id = 0;
    private String username = "";
    private String password = "";
    private EmployeeRole role;
    boolean loggedIn = false;

    Employee(String username, String password) {
	this.name = "";
	this.role = null;
	this.username = username;
	this.password = password;
	this.loggedIn = false;
	this.id = 0;
	if (login(username, password)) {
	    System.out.println("Employee Created and logged in");
	} else {
	    System.err.println("Employee Created but could not login!\nYou need to call the login function");
	}
    }

    public boolean login(String username, String password) {
	// TODO: call employee login API and fill the data from the response
	this.name = "";
	this.loggedIn = true;
	this.id = 1;
	int role = 1;

	// determine employee's role
	switch (role) {
	case 1: // manager
	    this.role = EmployeeRole.Manager;
	    break;
	case 2: // sales
	    this.role = EmployeeRole.Sales;
	    break;
	case 3: // warehouse
	    this.role = EmployeeRole.Warehouse;
	    break;
	default:
	    break;
	}

	// operation status
	if (this.loggedIn) {
	    return true;
	}
	return false;
    }

    public EmployeeRole getRole() {
	return this.role;
    }

    public boolean isLoggedIn() {
	return this.loggedIn;
    }

    public String getName() {
	return name;
    }

}
