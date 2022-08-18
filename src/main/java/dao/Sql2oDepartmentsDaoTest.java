package dao;

import models.Department;
import models.User;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class Sql2oDepartmentsDaoTest {
    @Test
    public void addDepartmentToUserAddsTypeCorrectly() throws Exception {

        User testUser = setupUser();
        User altUser = setupAltUser();

        UserDao.add(testUser);
        UserDao.add(altUser);

        Department testDepartment = setupNewDepartment();

        DepartmentDao.add(testDepartment);

        DepartmentDao.addDepartmentToUser(testDepartment, testUser);
        DepartmentDao.addDepartmentToUser(testDepartment, altUser);

        assertEquals(2, DepartmentDao.getAllUsersForADepartment(testDepartment.getId()).size());
    }

}
