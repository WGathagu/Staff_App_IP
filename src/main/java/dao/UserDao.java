package dao;
import models.User;
import java.util.List;

public interface UserDao {

        //create
        void add (User user);
        // void addRestaurantToFoodType(Restaurant restaurant, Foodtype foodtype)

        //read
        List<User> getAll();
        User findById(int id);
        // List<Foodtype> getAllFoodtypesForARestaurant(int restaurantId);

        //update
        void update(int id, String name, String phone, String email, String position, String department);

        //delete
        void deleteById(int id);
        void clearAll();
}
