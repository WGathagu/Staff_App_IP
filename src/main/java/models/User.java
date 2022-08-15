package models;

public class User {
    private String name;
    private String phone;
    private String email;
    private String position;
    private String department;

    private int staffid;

    public User(String name, String phone, String email, String position, String department) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.department = department;
    }
}

