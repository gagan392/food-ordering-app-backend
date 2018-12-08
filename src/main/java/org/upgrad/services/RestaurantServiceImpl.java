package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.Restaurant;
import org.upgrad.repositories.RestaurantRepository;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = restaurantRepository.getAll();
        return restaurants;
    }

    @Override
    public List<Restaurant> getByName(String restaurantName) {
        return restaurantRepository.getByName(restaurantName);
    }

    @Override
    public List<Restaurant> getByCategoryName(String categoryName) {
        return restaurantRepository.getByCategoryName(categoryName);
    }
}
