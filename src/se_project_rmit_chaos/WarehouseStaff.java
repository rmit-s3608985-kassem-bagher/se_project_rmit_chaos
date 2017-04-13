package se_project_rmit_chaos;

public class WarehouseStaff extends Employee {

    WarehouseStaff(String name, String username, String password) {
	super(name, username, password, EmployeeRole.Warehouse);
    }
}
