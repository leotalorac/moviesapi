package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.dto.MovieDTO;
import com.endava.movies.moviesapi.model.entities.GenreEntity;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.model.mapper.MovieMapper;
import com.endava.movies.moviesapi.repository.MovieRepository;
import com.endava.movies.moviesapi.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBList;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    @Autowired
    public MovieServiceImpl(MovieRepository repository){
        this.movieRepository=repository;
    }

    @Override
    public Optional<MovieDTO> getMovieById(Integer id){
        return movieRepository
                .findById(id)
                .map(MovieMapper.INSTANCE::movieEntityToMovieDTO);
    }
    @Override
    public Page<MovieEntity> getMoviesFilter(Integer pageNumber,Boolean adult, String title, String[] genres,Integer limit){
        Pageable page = PageRequest.of(pageNumber, limit);
        return movieRepository.filterMovies(title,adult, genres,page);
    }

    @Override
    public Integer saveMovies() {
        return saveMoviesThreads(getMoviesFromCsv("./data/movies_metadata.csv"));
    }
    public List<MovieEntity> getMoviesFromCsv(String path){
        List<MovieEntity> moviesToCharge = new ArrayList<>();
        try(CSVReader reader = new CSVReader(new FileReader(path))){
            String[] row;
            boolean first = false;
            while ((row = reader.readNext()) != null) {
                if(first){
                    try {
                        moviesToCharge.add(movieFromLine(row));
                    }catch (Exception e){
                        log.error("Error on registry parsing," + e.getMessage());
                    }
                }else{
                    first=true;
                }
            }
        }catch (Exception e){
            log.error("Error on csv file" + e.getMessage());
        }
        return moviesToCharge;
    }
    public Integer saveMoviesThreads(List<MovieEntity> movies){
        List<Uploader> uploaders = new ArrayList<>();
        int MAX = 1000;
        int index=0;
        while ( index<movies.size()){
            if(index+MAX > movies.size()){
                uploaders.add(new Uploader(movies.subList(index,movies.size()),movieRepository,"Thread "+ Integer.toString(index)));
            }else{
                uploaders.add(new Uploader(movies.subList(index,index+MAX),movieRepository,"Thread "+ Integer.toString(index)));
            }
            index+=MAX;
        }
        ExecutorService executor = Executors.newFixedThreadPool(uploaders.size());
        uploaders.forEach(executor::execute);
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {}
        executor.shutdown();
        System.out.println("\nFinished all threads");
        return movies.size();
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

}
