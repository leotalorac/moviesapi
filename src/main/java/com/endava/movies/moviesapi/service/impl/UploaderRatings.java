package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.entities.RatingEntity;
import com.endava.movies.moviesapi.repository.RatingRepository;

import java.util.List;

public class UploaderRatings implements Runnable{

    private List<RatingEntity> ratings;
    private RatingRepository ratingRepository;
    private String threadName;

    public UploaderRatings(List<RatingEntity> ratings, RatingRepository ratingRepository, String name){
        this.ratings=ratings;
        this.ratingRepository = ratingRepository;
        this.threadName=name;
    }
    @Override
    public void run() {
        ratingRepository.saveAll(this.ratings);
        this.ratings.clear();
    }
}
