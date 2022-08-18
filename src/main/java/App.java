import com.google.gson.Gson;
import com.sun.source.tree.NewArrayTree;
import dao.Sql2oDepartmentDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUserDao;
import dao.UserDao;
import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        Sql2oDepartmentDao departmentDao;
        Sql2oUserDao userDao;
        Sql2oNewsDao newsDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        userDao = new Sql2oUserDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        conn = sql2o.open();


        post("/user/new", "application/json", (req, res) -> {
            User restaurant = gson.fromJson(req.body(), User.class);
            userDao.add(restaurant);
            res.status(201);
            res.type("application/json");
            return gson.toJson(restaurant);
        });

        get("/users", "application/json", (req, res) -> {
            res.type("application/json");
            return gson.toJson(userDao.getAll());
        });

        get("/users/:id", "application/json", (req, res) -> {
            res.type("application/json");
            int userId = Integer.parseInt(req.params("id"));
            res.type("application/json");
            return gson.toJson(userDao.findById(userId));
        });

        post("/users/:userId/news/new", "application/json", (req, res) -> {
            int userId = Integer.parseInt(req.params("userId"));
            News news = gson.fromJson(req.body(), News.class);
            news.setUserId(userId);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        get("/department/:id/restaurants", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            else if (departmentDao.getAllUsersForADepartment(departmentId).size()==0){
                return "{\"message\":\"I'm sorry, but no users are listed for this department.\"}";
            }
            else {
                return gson.toJson(departmentDao.getAllUsersForADepartment(departmentId));
            }
        });
        post("/department/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        //READ
        get("/department", "application/json", (req, res) -> {
            System.out.println(User.getAll());

            if(UserDao.getAll().size() > 0){
                return gson.toJson(UserDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no restaurants are currently listed in the database.\"}";
            }

        });

        get("/users/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Department departmentToFind = departmentDao.findById(departmentId);
            List<News> allNews;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allNews = newsDao.getAllNewsByDepartment(departmentId);

            return gson.toJson(allNews);
        });

        get("/departments", "application/json", (req, res) -> {
            return gson.toJson(departmentDao.getAll());
        });


        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });

        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });

    }

    }
}