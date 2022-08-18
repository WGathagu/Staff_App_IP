package models;

import java.util.Objects;

public class User {
    private String name;
    private String phone;
    private String email;
    private String position;
    private String department;

    private int id;

    public User(String name, String phone, String email, String position, String department) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.department = department;
    }

        public String getName() {
            return name;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public int getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User)) return false;
            User that = (User) o;
            return id == that.id &&
                    Objects.equals(name, that.name) &&
                    Objects.equals(phone, that.phone) &&
                    Objects.equals(email, that.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, phone, email, id);
        }
}

