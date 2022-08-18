package dao;

import models.Department;
import models.User;

import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;


public class Sql2oUserDao implements UserDao {
    private final Sql2o sql2o;
    public Sql2oUserDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (name, phone, email, position, department) VALUES (:name, :phone, :email, :position, :department)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM users")
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public User findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM users WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(User.class);
        }
    }

    @Override
    public void update(int id, String newName, String newPhone, String newEmail, String newPosition, String department) {
        String sql = "UPDATE users SET (name, phone, email, position, department) = (:name, :phone, :email, :position, :department) WHERE id=:id"; //CHECK!!!
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("phone", newPhone)
                    .addParameter("email", newEmail)
                    .addParameter("position", newPosition)
                    .addParameter("department", department)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from users WHERE id = :id"; //raw sql
        String deleteJoin = "DELETE from users_departments WHERE userid = :userId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("userId", id)
                    .executeUpdate();

        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from users";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addUserToDepartment(User user, Department department){
        String sql = "INSERT INTO users_departments (userid, departmentid) VALUES (:userid, :departmentid)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userid", user.getId())
                    .addParameter("departmentid", department.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAllDepartmentsByUsers(int userid){
        List<Department> departmnt = new ArrayList(); //empty list
        String joinQuery = "SELECT departmentid FROM users_departments WHERE userid = :userid";

        try (Connection con = sql2o.open()) {
            List<Integer> alldepartmentids = con.createQuery(joinQuery)
                    .addParameter("userid", userid)
                    .executeAndFetch(Integer.class);
            for (Integer departmentid : alldepartmentids){
                String alldepartmentQuery = "SELECT * FROM departments WHERE id = :departmentid";
                departmnt.add(
                        con.createQuery(alldepartmentQuery)
                                .addParameter("departmentid", departmentid)
                                .executeAndFetchFirst(Department.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departmnt;
    }
}

