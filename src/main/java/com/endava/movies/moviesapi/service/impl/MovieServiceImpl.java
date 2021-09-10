package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.dto.MovieDTO;
import com.endava.movies.moviesapi.model.entities.GenreEntity;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.model.mapper.MovieMapper;
import com.endava.movies.moviesapi.repository.MovieRepository;
import com.endava.movies.moviesapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
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
    public Optional<List<MovieEntity>> getMoviesFilter(Boolean adult, String title, List<GenreEntity> genres){
        return movieRepository.findByAdultAndTitleAndGenres(adult,title,genres);
    }

    @Override
    public void saveMovies(List<MovieEntity> movies) {
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
        while (!executor.isTerminated()) {

        }
        executor.shutdown();
        System.out.println("\nFinished all threads");
    }


}
