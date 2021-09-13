package com.endava.movies.moviesapi.service;

import com.endava.movies.moviesapi.model.dto.MovieDTO;
import com.endava.movies.moviesapi.model.entities.GenreEntity;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    Optional<MovieDTO> getMovieById(Integer id);
    void saveMovies(List<MovieEntity> movies);
    Page<MovieEntity> getMoviesFilter(Integer page,Boolean adult, String title, List<GenreEntity> genres,Integer limit);
}
