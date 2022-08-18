package dao;

import models.Department;
import models.User;

import java.util.List;

public interface DepartmentDao {

        //create
        void add(Department department);
        void addDepartmenttoUser(Department department, User user);

        //read
        List<Department> getAll();
        static List<User> getAllUsersForADepartment(int id);

        //update
        //omit for now

        //delete
        void deleteById(int id);
        void clearAll();

