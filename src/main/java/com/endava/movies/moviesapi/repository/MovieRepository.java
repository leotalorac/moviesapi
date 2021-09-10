package com.endava.movies.moviesapi.repository;

import com.endava.movies.moviesapi.model.entities.GenreEntity;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<MovieEntity,Integer>{
    Optional<List<MovieEntity>> findByAdultOrTitleOrGenres(Boolean adult, String title, List<GenreEntity> genres);
    Optional<List<MovieEntity>> findByAdultAndTitleAndGenres(Boolean adult, String title, List<GenreEntity> genres);
}
