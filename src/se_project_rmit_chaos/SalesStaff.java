package se_project_rmit_chaos;

class SalesStaff extends Employee {

    SalesStaff(String name, String username, String password) {
	super(name, username, password, EmployeeRole.Sales);
    }
}
