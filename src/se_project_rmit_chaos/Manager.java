package se_project_rmit_chaos;

class Manager extends Employee {

    Manager(String name, String username, String password) {
	super(name, username, password,EmployeeRole.Manager);
    }
}
