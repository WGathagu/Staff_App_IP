package dao;

import models.Department;
import models.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentDao {

    @Override
    public void addDepartmentToUser(Department department, User user){
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

}
