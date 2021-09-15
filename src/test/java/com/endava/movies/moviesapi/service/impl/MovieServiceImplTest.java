package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.dto.MovieDTO;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.repository.MovieRepository;
import com.endava.movies.moviesapi.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class MovieServiceImplTest {
    @Autowired
    MovieService movieService;

    @MockBean
    MovieRepository movieRepository;
    @MockBean
    Uploader uploader;

    @Test
    void movie_getById_getOne() {
        MovieEntity movie = MovieEntity.builder().id(10).adult(true).title("Super movie").build();
        when(movieRepository.findById(10)).thenReturn(Optional.ofNullable(movie));
        Optional<MovieDTO> result =  movieService.getMovieById(10);
        assertTrue(result.isPresent());
        assertNotNull(result.get());
    }

    @Test
    void movies_filter_getAll() {
        Page<MovieEntity> movie = new PageImpl<>(Collections.singletonList(MovieEntity.builder().id(10).adult(true).title("Super movie").genres(Collections.singletonList("Horror")).build()));
        Pageable page = PageRequest.of(1, 10);
        when(movieRepository.filterMovies("Super movie",true,new String[]{"Horror"},page)).thenReturn(movie);
        Page<MovieEntity> result =  movieService.getMoviesFilter(1,true,"Super movie", new String[]{"Horror"},10,null);
        assertNotNull(result);
        assertTrue(result.hasContent());
        System.out.println(movie);
    }

}