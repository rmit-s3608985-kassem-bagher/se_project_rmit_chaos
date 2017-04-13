package se_project_rmit_chaos;

public class Employee {
    String name;
    String username;
    String password;
    EmployeeRole role;
    
    Employee(String name, String username, String password,EmployeeRole role) {
	this.name = name;
	this.username = username;
	this.password = password;
	this.role = role;
    }

    public String getName() {
	return name;
    }

    public String getUsername() {
	return username;
    }

    public String getPassword() {
	return password;
    }
}
