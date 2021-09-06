package com.endava.movies.moviesapi.repository;

import com.endava.movies.moviesapi.model.entities.MovieEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<MovieEntity,Integer>{

}
