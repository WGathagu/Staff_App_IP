package dao;

import models.Department;
import java.util.List;

public interface DepartmentDao {

        //create
        void add(Department department);
        //void addFoodtypeToRestaurant(Foodtype foodtype, Restaurant restaurant);

        //read
        List<Department> getAll();
        // List<Restaurant> getAllRestaurantsForAFoodtype(int id);

        //update
        //omit for now

        //delete
        void deleteById(int id);
        void clearAll();
}
