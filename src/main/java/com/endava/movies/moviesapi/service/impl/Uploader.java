package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.repository.MovieRepository;

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
    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
