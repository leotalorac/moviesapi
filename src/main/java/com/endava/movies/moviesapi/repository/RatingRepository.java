package com.endava.movies.moviesapi.repository;

import com.endava.movies.moviesapi.model.entities.RatingEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends MongoRepository<RatingEntity,Integer> {
    Optional<List<RatingEntity>> findAllByMovieId(Integer movieId);

}
