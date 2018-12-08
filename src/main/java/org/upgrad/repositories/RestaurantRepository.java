package org.upgrad.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.models.Restaurant;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM RESTAURANT ORDER BY USER_RATING DESC ")
    List<Restaurant> getAll();

    @Query(nativeQuery = true, value = "SELECT * FROM RESTAURANT WHERE LOWER(RESTAURANT_NAME) LIKE LOWER(concat('%', ?1, '%')) ORDER BY USER_RATING DESC ")
    List<Restaurant> getByName(String restaurantName);

}
