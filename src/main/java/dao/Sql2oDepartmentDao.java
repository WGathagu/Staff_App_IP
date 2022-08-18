package dao;

import models.Department;
import models.User;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentDao implements DepartmentDao{
    private final Sql2o sql2o;
    public Sql2oDepartmentDao(Sql2o sql2o){ this.sql2o = sql2o; }

    @Override
    public void add(Department department) {
        String sql = "INSERT INTO departments (name, description) VALUES (:name, :description)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Department> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Department.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from departments WHERE id=:id";
        String deleteJoin = "DELETE from users_departments WHERE departmentid = :departmentId";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();

            con.createQuery(deleteJoin)
                    .addParameter("departmentId", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        String sql = "DELETE from departments";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addDepartmentToUser(Department department, User user){
        String sql = "INSERT INTO users_departments (userid, departmentid) VALUES (:userId, :departmentId)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("userId", user.getId())
                    .addParameter("departmentId", department.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<User> getAllUsersForADepartment(int departmentid) {
        ArrayList<User> user = new ArrayList<>();

        String joinQuery = "SELECT userid FROM users_departments WHERE departmentid = :departmentid";

        try (Connection con = sql2o.open()) {
            List<Integer> allUserIds = con.createQuery(joinQuery)
                    .addParameter("departmentid", departmentid)
                    .executeAndFetch(Integer.class);
            for (Integer userId : allUserIds){
                String userQuery = "SELECT * FROM users WHERE id = :userId";
                user.add(
                        con.createQuery(userQuery)
                                .addParameter("userId", userId)
                                .executeAndFetchFirst(User.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return user;
    }


    @Override
    public Department findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Department.class);
        }
    }

}
