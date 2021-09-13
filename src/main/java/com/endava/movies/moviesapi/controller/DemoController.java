package com.endava.movies.moviesapi.controller;

import com.endava.movies.moviesapi.model.dto.MovieDTO;
import com.endava.movies.moviesapi.model.entities.GenreEntity;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.model.entities.RatingEntity;
import com.endava.movies.moviesapi.model.mapper.MovieMapper;
import com.endava.movies.moviesapi.service.MovieService;
import com.endava.movies.moviesapi.service.RatingService;
import com.endava.movies.moviesapi.service.impl.GenreParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.CSVReader;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Executable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("v1/demo-service")
public class DemoController {

    MovieService movieServiceImpl;
    RatingService ratingServiceImpl;
    @Autowired
    public DemoController(MovieService movieServiceImpl,RatingService ratingServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
        this.ratingServiceImpl=ratingServiceImpl;
    }

    //    Simple get request
    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable("id") Integer id){
            return movieServiceImpl
                    .getMovieById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(()->ResponseEntity.noContent().build());
    }
    @GetMapping("/movie/{id}/rating")
    public ResponseEntity<List<RatingEntity>> getRatingByMovieId(@PathVariable("id") Integer id){
        return ratingServiceImpl
                .getRatingsByMovie(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.noContent().build());

    }

    @GetMapping("movies/")
    public ResponseEntity<List<MovieEntity>> getMovies(@RequestParam(required = false) Boolean adult,
                                                       @RequestParam(required = false) List<GenreEntity> genres,
                                                       @RequestParam(required = false) String title,
                                                       @RequestParam(required = false) Integer limit){
//        List<GenreEntity> genres = new ArrayList<>();
//        Arrays.stream(genre.split(",")).forEach(g ->{
//            genres.add(new GenreEntity())
//        });
        return movieServiceImpl
                .getMoviesFilter(adult,title,genres)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.noContent().build());
    }
    @GetMapping("/load/movies")
    public ResponseEntity<String> uploadMovies(){
        try(CSVReader reader = new CSVReader(new FileReader("./data/movies_metadata.csv"))){
            String[] row;
            List<MovieEntity> moviesToCharge = new ArrayList<>();
            boolean first = false;
            while ((row = reader.readNext()) != null) {
                if(first){
                    try {
                        moviesToCharge.add(movieFromLine(row));
                    }catch (Exception e){
                        log.error("Error ," , e);
                    }
                }else{
                    first=true;
                }
            }
            movieServiceImpl.saveMovies(moviesToCharge);
            return ResponseEntity.ok("Uploaded");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }
    //builder
    public MovieEntity movieFromLine(String[] line) throws JsonProcessingException {
        return MovieEntity.builder()
                .id(Integer.parseInt(line[5]))
                .originalTitle(line[8])
                .adult(Boolean.parseBoolean(line[0]))
                .genres(new GenreParser().parseJson(line[3]))
                .overview(line[9])
                .originalLanguage(line[7])
                .ratings(new ArrayList<>())
                .budget(Integer.parseInt(line[2]))
                .title(line[20])
                .build();
    }

    @GetMapping("/load/ratings")
    public ResponseEntity<String> uploadRatings(){
        try(CSVReader reader = new CSVReader(new FileReader("./data/ratings.csv"))){
            String[] row;
            List<RatingEntity> ratingsToCharge = new ArrayList<>();
            boolean first = false;
            Integer id= 0;
            while ((row = reader.readNext()) != null) {
                if(first){
                    try {
                        ratingsToCharge.add(ratingsFromLine(row,id));
                        id+=1;
                        if(id%50000==0){
                            ratingServiceImpl.saveRatings(ratingsToCharge);
                            ratingsToCharge.clear();
                        }
                    }catch (Exception e){
                        log.error("Error ," , e);
                    }
                }else{
                    first=true;
                }
            }
            ratingServiceImpl.saveRatings(ratingsToCharge);
            ratingsToCharge.clear();
            return ResponseEntity.ok("Uploaded");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
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
