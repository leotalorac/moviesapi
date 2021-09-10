package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.model.entities.RatingEntity;
import com.endava.movies.moviesapi.repository.MovieRepository;
import com.endava.movies.moviesapi.repository.RatingRepository;
import com.endava.movies.moviesapi.service.RatingService;

import java.util.List;

public class Uploader implements Runnable{
    private List<MovieEntity> movies;
    private MovieRepository movieRepository;
    private Thread t;
    private String threadName;

    public Uploader(List<MovieEntity> movies, MovieRepository movieRepository,String name){
        this.movies=movies;
        this.movieRepository = movieRepository;
        this.threadName=name;
    }
    @Override
    public void run() {
        movieRepository.saveAll(this.movies);

    }
}
