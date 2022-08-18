import com.google.gson.Gson;
import dao.Sql2oDepartmentDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUserDao;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.sql.Connection;

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
    }
}