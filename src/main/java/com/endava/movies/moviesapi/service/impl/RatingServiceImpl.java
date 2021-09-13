package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.entities.GenreEntity;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.model.entities.RatingEntity;
import com.endava.movies.moviesapi.repository.MovieRepository;
import com.endava.movies.moviesapi.repository.RatingRepository;
import com.endava.movies.moviesapi.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.spi.ServiceRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository repository){
        this.ratingRepository=repository;
    }

    public Optional<List<RatingEntity>> getRatingsByMovie(Integer movieId){
        return ratingRepository.findByMovieId(movieId);
    }

    @Override
    public void saveRatings(List<RatingEntity> ratings) {
        List<UploaderRatings> uploaders = new ArrayList<>();
        int MAX = 5000;
        int index=0;
        while ( index<ratings.size()){
            if(index+MAX > ratings.size()){
                uploaders.add(new UploaderRatings(ratings.subList(index,ratings.size()),ratingRepository,"Thread "+ Integer.toString(index)));
            }else{
                uploaders.add(new UploaderRatings(ratings.subList(index,index+MAX),ratingRepository,"Thread "+ Integer.toString(index)));
            }
            index+=MAX;

        }
        ExecutorService executor = Executors.newFixedThreadPool(uploaders.size());
        uploaders.forEach(executor::execute);
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {

        }
        executor.shutdown();
        System.out.println("\nFinished all threads");
    }
}
