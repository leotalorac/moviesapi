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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
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
public class MoviesApiController {
    private final MovieService movieServiceImpl;
    private final RatingService ratingServiceImpl;

    @Autowired
    public MoviesApiController(MovieService movieServiceImpl, RatingService ratingServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
        this.ratingServiceImpl=ratingServiceImpl;
    }

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

    @GetMapping("/movies/{page}")
    public ResponseEntity<Page<MovieEntity>> getMovies(@PathVariable("page") Integer page,
                                                       @RequestParam(required = false) Boolean adult,
                                                       @RequestParam(required = false) String genres,
                                                       @RequestParam(required = false) String title,
                                                       @RequestParam(required = false,defaultValue = "100") Integer limit,
                                                       @RequestParam(required = false,defaultValue = "title") String sortParam){
        String[] genresSent = {};
        if(genres!=null){
           genresSent= genres.split(",");
        }
        return ResponseEntity.ok(movieServiceImpl
                .getMoviesFilter(page,adult,title,genresSent,limit,sortParam));
    }

    @PostMapping("/load/movies")
    public ResponseEntity<String> uploadMovies(){
        movieServiceImpl.saveMovies();
        return ResponseEntity.ok("Movies uploaded");
    }

    @PostMapping("/load/ratings")
    public ResponseEntity<String> uploadRatings(){
        ratingServiceImpl.saveRatings();
        return ResponseEntity.ok("Ratings Uploaded");
    }

}
