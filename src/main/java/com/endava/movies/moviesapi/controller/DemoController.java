package com.endava.movies.moviesapi.controller;

import com.endava.movies.moviesapi.model.dto.MovieDTO;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.model.mapper.MovieMapper;
import com.endava.movies.moviesapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/demo-service")
public class DemoController {

    MovieService movieServiceImpl;
    @Autowired
    public DemoController(MovieService movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
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
}
