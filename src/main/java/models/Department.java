package models;

public class Department {
    private String departmentName;
    private String departmentDescription;
    private int noEmployees;
    private int departmentid;
    private int userId;

    public Department(String departmentName, String departmentDescription, int noEmployees, int userId) {
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.noEmployees = noEmployees;
        this.userId = userId;
    }
}
