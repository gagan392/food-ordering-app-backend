package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.Restaurant;
import org.upgrad.models.RestaurantCategory;
import org.upgrad.requestResponseEntity.RestaurantResponse;
import org.upgrad.services.RestaurantService;

import javax.tools.JavaCompiler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    /*
    * This endpoint is used to list down all the Restaurants with categories and address.
    * sorting restaurants based on user rating
    * categories string in an alphabetical order
    */
    @GetMapping("/")
    @CrossOrigin
    public ResponseEntity<?> fetchRestaurantList() {
        List<Restaurant> restaurants = restaurantService.getAll();
        return new ResponseEntity<>(mapRestaurantResponse(restaurants), HttpStatus.OK);
    }

    /*
     * This endpoint is used to list down only the Restaurants having user provided name
     * name is partial/exact match, case insensitive match
     * sorting restaurants based on user rating
     * categories string in an alphabetical order
     */
    @GetMapping("name/{reastaurantName}")
    @CrossOrigin
    public ResponseEntity<?> fetchRestaurantByName(@PathVariable String reastaurantName) {
        List<Restaurant> restaurants = restaurantService.getByName(reastaurantName);
        if(restaurants.size() == 0) {
            return new ResponseEntity<Object>("No Restaurant by this name!", HttpStatus.OK);
        }
        return new ResponseEntity<Object>(mapRestaurantResponse(restaurants), HttpStatus.OK);
    }


    /*
     * This endpoint is used to list down only the Restaurants having user provided category name
     * category name is partial/exact match, case insensitive match
     * sorting restaurants in alphabetical order
     * categories string in an alphabetical order
     */
    @GetMapping("category/{categoryName}")
    @CrossOrigin
    public ResponseEntity<?> fetchRestaurantByCategoryName(@PathVariable String categoryName) {
        List<Restaurant> restaurants = restaurantService.getByCategoryName(categoryName);
        if(restaurants.size() == 0) {
            return new ResponseEntity<Object>("No Restaurant by this category!", HttpStatus.OK);
        }
        return new ResponseEntity<Object>(mapRestaurantResponse(restaurants), HttpStatus.OK);
    }

    List<RestaurantResponse> mapRestaurantResponse(List<Restaurant> restaurants) {
        List<RestaurantResponse> restaurantResponses = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            List<RestaurantCategory> restaurantCategories = restaurant.getRestaurantCategories();
            List<String> categories = new ArrayList<>();
            for (RestaurantCategory restaurantCategory : restaurantCategories) {
                categories.add(restaurantCategory.getCategory().getCategoryName());
            }
            Collections.sort(categories);
            String categoriesCsv = String.join(",", categories);
            restaurantResponses.add(new RestaurantResponse(restaurant.getId(), restaurant.getRestaurantName(), restaurant.getPhotoUrl(), restaurant.getUserRating(), restaurant.getAvgPrice(), restaurant.getNumberUsersRated(), restaurant.getAddress(), categoriesCsv));
        }
        return  restaurantResponses;
    }
}
