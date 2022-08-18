package dao;
import models.Department;
import models.User;
import java.util.List;

public interface UserDao {

        //create
        void add(User user);
        void addUserToDepartment(User user, Department department);

        //read
        static List<User> getAll();
        User findById(int id);
        static List<Department> getAllDepartmentsForAUser(int userId);

        //update
        void update(int id, String name, String phone, String email, String position, String department);

        //delete
        void deleteById(int id);
        void clearAll();


