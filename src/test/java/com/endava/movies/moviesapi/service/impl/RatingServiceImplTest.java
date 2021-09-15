package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.dto.MovieDTO;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import com.endava.movies.moviesapi.model.entities.RatingEntity;
import com.endava.movies.moviesapi.repository.MovieRepository;
import com.endava.movies.moviesapi.repository.RatingRepository;
import com.endava.movies.moviesapi.service.MovieService;
import com.endava.movies.moviesapi.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class RatingServiceImplTest {
    @Autowired
    RatingService ratingService;

    @MockBean
    RatingRepository ratingRepository;

    @Test
    void rating_getByMovieId_getAll() {
        RatingEntity rating = RatingEntity.builder().id(10).userId(1).movieId(1).rating(2.0f).build();
        when(ratingRepository.findAllByMovieId(1)).thenReturn(Optional.of(Collections.singletonList(rating)));
        Optional<List<RatingEntity>> result =  ratingService.getRatingsByMovie(1);
        assertTrue(result.isPresent());
        assertNotNull(result.get());
    }
}