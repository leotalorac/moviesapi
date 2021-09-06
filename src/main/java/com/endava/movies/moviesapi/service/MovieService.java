package com.endava.movies.moviesapi.service;

import com.endava.movies.moviesapi.model.dto.MovieDTO;
import com.endava.movies.moviesapi.model.entities.MovieEntity;

import java.util.Optional;

public interface MovieService {
    Optional<MovieDTO> getMovieById(Integer id);
}
