package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.dto.MovieDTO;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.model.mapper.MovieMapper;
import com.endava.movies.moviesapi.repository.MovieRepository;
import com.endava.movies.moviesapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
