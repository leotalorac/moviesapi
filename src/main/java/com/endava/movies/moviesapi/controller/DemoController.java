package com.endava.movies.moviesapi.controller;

import com.endava.movies.moviesapi.model.dto.MovieDTO;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.model.mapper.MovieMapper;
import com.endava.movies.moviesapi.service.MovieService;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
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
    @GetMapping("/load/movies")
    public ResponseEntity<String> uploadMovies(){
        try(CSVReader reader = new CSVReader(new FileReader("./data/movies_metadata.csv"))){
            String[] row;
            List<MovieEntity> moviesToCharge = new ArrayList<>();
            boolean first = false;
            while ((row = reader.readNext()) != null) {
                if(first){
                    try {
                        moviesToCharge.add(movieFromList(row));
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
    public MovieEntity movieFromList(String[] line){
        return new MovieEntity(
                Integer.parseInt(line[5]),
                line[8],
                Boolean.parseBoolean(line[0]),
                Arrays.asList("muchos","mas"),
                line[9],
                line[4],
                new ArrayList<>());
    }

}
