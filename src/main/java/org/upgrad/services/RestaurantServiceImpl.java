package org.upgrad.services;

import com.google.common.collect.Lists;
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
}
