package dao;

import models.Department;
import models.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Sql2oUserDao {

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
    public List<Department> getAllDepartmentsByUser(int userid){
        ArrayList<Department> department = new ArrayList<>();

        String joinQuery = "SELECT departmentid FROM users_departments WHERE userid = :userid";

        try (Connection con = sql2o.open()) {
            List<Integer> allDepartmantIds = con.createQuery(joinQuery)
                    .addParameter("userid", userid)
                    .executeAndFetch(Integer.class);
            for (Integer departmentid : allDepartmantIds){
                String departmentQuery = "SELECT * FROM departments WHERE id = :departmentid";
                department.add(
                        con.createQuery(departmentQuery)
                                .addParameter("departmentid", departmentid)
                                .executeAndFetchFirst(Department.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return department;
    }
}
