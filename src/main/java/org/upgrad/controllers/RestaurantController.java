package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.models.*;
import org.upgrad.requestResponseEntity.CategoryResponse;
import org.upgrad.requestResponseEntity.RestaurantResponse;
import org.upgrad.requestResponseEntity.RestaurantResponseCategorySet;
import org.upgrad.services.RestaurantService;
import org.upgrad.services.UserAuthTokenService;

import java.util.*;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserAuthTokenService userAuthTokenService;

    /*
    * This endpoint is used to list down all the Restaurants with categories and address.
    * sorting restaurants based on user rating
    * categories string in an alphabetical order
    */
    @GetMapping("/")
    @CrossOrigin
    public ResponseEntity<?> fetchRestaurantList() {
        List<Restaurant> restaurants = restaurantService.getAll();
        return new ResponseEntity<>(mapRestaurantResponseList(restaurants), HttpStatus.OK);
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
        if (restaurants.size() == 0) {
            return new ResponseEntity<Object>("No Restaurant by this name!", HttpStatus.OK);
        }
        return new ResponseEntity<Object>(mapRestaurantResponseList(restaurants), HttpStatus.OK);
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
        if (restaurants.size() == 0) {
            return new ResponseEntity<Object>("No Restaurant by this category!", HttpStatus.OK);
        }
        return new ResponseEntity<Object>(mapRestaurantResponseList(restaurants), HttpStatus.OK);
    }

    /*
     * This endpoint is used to list down only the Restaurants having user provided category name
     * category name is partial/exact match, case insensitive match
     * sorting restaurants in alphabetical order
     * categories string in an alphabetical order
     */
    @GetMapping("/{restaurantId}")
    @CrossOrigin
    public ResponseEntity<?> fetchRestaurantById(@PathVariable int restaurantId) {
        Restaurant restaurant = restaurantService.getById(restaurantId);
        if (restaurant == null) {
            return new ResponseEntity<Object>("No Restaurant by this id!", HttpStatus.OK);
        }
        return new ResponseEntity<Object>(mapRestaurantResponseCategorySet(restaurant), HttpStatus.OK);
    }

    /*
     * This endpoint is used to update the Restaurants by id
     * user authentication
     */
    @PutMapping("/{restaurantId}")
    @CrossOrigin
    public ResponseEntity<?> updateRestaurantById(@RequestHeader String accessToken, @PathVariable int restaurantId, @RequestParam double restaurantRating) {
        if (userAuthTokenService.isUserLoggedIn(accessToken) == null) {
            return new ResponseEntity<>("Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        } else if (userAuthTokenService.isUserLoggedIn(accessToken).getLogoutAt() != null) {
            return new ResponseEntity<>("You have already logged out. Please Login first to access this endpoint!", HttpStatus.UNAUTHORIZED);
        }

        Restaurant restaurant = restaurantService.getById(restaurantId);
        if (restaurant == null) {
            return new ResponseEntity<Object>("No Restaurant by this id!", HttpStatus.OK);
        }

        double avgUserRating = restaurant.getUserRating();
        double updatedRating = (avgUserRating * restaurant.getNumberUsersRated() + restaurantRating) / (restaurant.getNumberUsersRated() + 1);
        restaurant.setNumberUsersRated(restaurant.getNumberUsersRated() + 1);
        restaurant.setUserRating(updatedRating);
        restaurantService.update(restaurant);

        return new ResponseEntity<Object>(mapRestaurantResponse(restaurant), HttpStatus.OK);
    }

    RestaurantResponse mapRestaurantResponse(Restaurant restaurant) {
        List<String> categories = new ArrayList<>();
        for (RestaurantCategory restaurantCategory : restaurant.getRestaurantCategories()) {
            categories.add(restaurantCategory.getCategory().getCategoryName());
        }
        Collections.sort(categories);
        String categoriesCsv = String.join(",", categories);
        RestaurantResponse restaurantResponse = new RestaurantResponse(restaurant.getId(), restaurant.getRestaurantName(), restaurant.getPhotoUrl(), restaurant.getUserRating(), restaurant.getAvgPrice(), restaurant.getNumberUsersRated(), restaurant.getAddress(), categoriesCsv);
        return restaurantResponse;
    }

    List<RestaurantResponse> mapRestaurantResponseList(List<Restaurant> restaurants) {
        List<RestaurantResponse> restaurantResponses = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            restaurantResponses.add(mapRestaurantResponse(restaurant));
        }
        return restaurantResponses;
    }

    RestaurantResponseCategorySet mapRestaurantResponseCategorySet(Restaurant restaurant) {
        List<RestaurantCategory> restaurantCategories = restaurant.getRestaurantCategories();
        Set<CategoryResponse> categoryResponses = new HashSet<>();
        for (RestaurantCategory restaurantCategory : restaurantCategories) {
            Category category = restaurantCategory.getCategory();
            Set<Item> items = new HashSet<>();
            for (CategoryItem categoryItem : category.getCategoryItems()) {
                items.add(categoryItem.getItem());
            }
            categoryResponses.add(new CategoryResponse(category.getId(), category.getCategoryName(), items));
        }
        RestaurantResponseCategorySet restaurantResponseCategorySet = new RestaurantResponseCategorySet(restaurant.getId(), restaurant.getRestaurantName(), restaurant.getPhotoUrl(), restaurant.getUserRating(), restaurant.getAvgPrice(), restaurant.getNumberUsersRated(), restaurant.getAddress(), categoryResponses);
        return restaurantResponseCategorySet;
    }
}
