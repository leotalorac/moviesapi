package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.entities.GenreEntity;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.model.entities.RatingEntity;
import com.endava.movies.moviesapi.repository.MovieRepository;
import com.endava.movies.moviesapi.repository.RatingRepository;
import com.endava.movies.moviesapi.service.RatingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.spi.ServiceRegistry;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Service
@Slf4j
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository repository){
        this.ratingRepository=repository;
    }

    public Optional<List<RatingEntity>> getRatingsByMovie(Integer movieId){
        return ratingRepository.findAllByMovieId(movieId);
    }

    @Override
    public Integer saveRatings() {
        return getRatingsFromCsv("./data/ratings.csv");
    }

    public Integer getRatingsFromCsv(String path){
        int id= 0;
        try(CSVReader reader = new CSVReader(new FileReader(path))){
            String[] row;
            List<RatingEntity> ratingsToCharge = new ArrayList<>();
            boolean first = false;

            while ((row = reader.readNext()) != null) {
                if(first){
                    try {
                        ratingsToCharge.add(ratingsFromLine(row,id));
                        id+=1;
                        if(id%50000==0){
                            this.saveRatingsThreads(ratingsToCharge);
                            ratingsToCharge.clear();
                        }
                    }catch (Exception e){
                        log.error("Error ," , e);
                    }
                }else{
                    first=true;
                }
            }
            this.saveRatingsThreads(ratingsToCharge);
            ratingsToCharge.clear();
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public void saveRatingsThreads(List<RatingEntity> ratings) {
        List<UploaderRatings> uploaders = new ArrayList<>();
        int MAX = 5000;
        int index = 0;
        while (index < ratings.size()) {
            if (index + MAX > ratings.size()) {
                uploaders.add(new UploaderRatings(ratings.subList(index, ratings.size()), ratingRepository, "Thread " + Integer.toString(index)));
            } else {
                uploaders.add(new UploaderRatings(ratings.subList(index, index + MAX), ratingRepository, "Thread " + Integer.toString(index)));
            }
            index += MAX;
        }
        ExecutorService executor = Executors.newFixedThreadPool(uploaders.size());
        uploaders.forEach(executor::execute);
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {}
        executor.shutdown();
        System.out.println("\nFinished all threads");
    }

    //builder
    public RatingEntity ratingsFromLine(String[] line,Integer id) throws JsonProcessingException {
        return RatingEntity.builder()
                .id(id)
                .userId(Integer.parseInt(line[0]))
                .movieId(Integer.parseInt(line[1]))
                .rating(Float.parseFloat(line[2]))
                .timestamp(line[3])
                .build();
    }

}
