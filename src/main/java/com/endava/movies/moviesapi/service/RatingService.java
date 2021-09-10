package com.endava.movies.moviesapi.service;

import com.endava.movies.moviesapi.model.entities.RatingEntity;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    Integer saveRatings(List<RatingEntity> ratings);

    Optional<List<RatingEntity>> getRatingsByMovie(Integer id);
}
