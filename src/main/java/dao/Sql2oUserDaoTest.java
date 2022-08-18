package dao;

import models.Department;
import models.User;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.AssertJUnit.assertEquals;

public class Sql2oUserDaoTest {
    @Test
    public void UserReturnsDepartmentsCorrectly() throws Exception {
        Department testDepartment  = new Department("IT", "Information Technology", 1, 1);
        DepartmentDao.add(testDepartment);

        Department otherDepartment  = new Department("R&D", "Research and Development", 1, 1);
        DepartmentDao.add(otherDepartment);

        User testUser = setupUser();
        UserDao.add(testUser);
        UserDao.addUserToDepartment(testUser,testDepartment);
        UserDao.addUserToDepartment(testUser,otherDepartment);

        Department[] Departments = {testDepartment, otherDepartment};

        assertEquals(Arrays.asList(Departments), UserDao.getAllDepartmentsForAUser(testUser.getId()));
    }
}
