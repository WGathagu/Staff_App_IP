package models;

import java.util.Objects;

public class Department {
    private String departmentName;
    private String departmentDescription;
    private int noEmployees;
    private int id;
    private int userId;

    public Department(String departmentName, String departmentDescription, int noEmployees, int userId) {
        this.departmentName = departmentName;
        this.departmentDescription = departmentDescription;
        this.noEmployees = noEmployees;
        this.userId = userId;
    }

        public String getName() {
            return departmentName;
        }

        public int getId() {
            return id;
        }

        public void setName(String name) {
            this.departmentName = name;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Department)) return false;
            Department department = (Department) o;
            return id == department.id &&
                    Objects.equals(departmentName, department.departmentName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(departmentName, id);
        }
}
