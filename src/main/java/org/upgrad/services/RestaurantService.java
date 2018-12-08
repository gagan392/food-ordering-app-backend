package org.upgrad.services;

import org.upgrad.models.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll();

    List<Restaurant> getByName(String restaurantName);

    List<Restaurant> getByCategoryName(String categoryName);
}
