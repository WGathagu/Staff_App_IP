package dao;

import models.User;
import models.Department;
import models.News;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oNewsDaoTest {
    private Connection conn;
    private Sql2oNewsDao NewsDao;
    private Sql2oDepartmentsDao DepartmentsDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        NewsDao = new Sql2oNewsDao(sql2o);
        DepartmentsDao = new Sql2oDepartmentsDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingNewsSetsId() throws Exception {
        News testNews = setupNews();
        assertEquals(1, testNews.getId());
    }

    @Test
    public void getAll() throws Exception {
        News News1 = setupNews();
        News News2 = setupNews();
        assertEquals(2, NewsDao.getAll().size());
    }

    @Test
    public void getAllNewssByDepartments() throws Exception {
        Departments testDepartments = setupDepartments();
        Departments otherDepartments = setupDepartments(); //add in some extra data to see if it interferes
        News News1 = setupNewsForDepartments(testDepartments);
        News News2 = setupNewsForDepartments(testDepartments);
        News NewsForOtherDepartments = setupNewsForDepartments(otherDepartments);
        assertEquals(2, NewsDao.getAllNewssByDepartments(testDepartments.getId()).size());
    }

    @Test
    public void deleteById() throws Exception {
        News testNews = setupNews();
        News otherNews = setupNews();
        assertEquals(2, NewsDao.getAll().size());
        NewsDao.deleteById(testNews.getId());
        assertEquals(1, NewsDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        News testNews = setupNews();
        News otherNews = setupNews();
        NewsDao.clearAll();
        assertEquals(0, NewsDao.getAll().size());
    }

    //helpers

    public News setupNews() {
        News news = new News("Staff General Meeting", "Departmental CHanges", 1);
        NewsDao.add(news);
        return news;
    }

    public News setupNewsForDepartments(Departments departments) {
        News news = new News("great", "Kim", 4, departments.getId());
        NewsDao.add(news);
        return news;
    }

    public Departments setupDepartments() {
        Departments departments = new Departments("Research and Development", "R&D for innovation and technology growth");
        DepartmentsDao.add(departments);
        return Departments;
    }
}