package com.endava.movies.moviesapi.repository;

import com.endava.movies.moviesapi.model.entities.GenreEntity;
import com.endava.movies.moviesapi.model.entities.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<MovieEntity,Integer>{
        @Query("{ '$and' :[" +
                "{ '$or' : [ { $expr: { $eq: ['?1', 'null'] } }, { 'adult' : ?1 } ] }," +
                "{ '$or' : [ { $expr: { $eq: ['?0', 'null'] } }, { 'title' : {'$regex': /?0/, '$options': 'i'} } ] },"+
                "{ '$or' : [ { $expr: { $eq: ['?2', '[]'] } }, { 'genres' :{ '$all' : ?2 } } ] }"+
                "]}")
    Page<MovieEntity> filterMovies( String title, Boolean adult,String[] genres, Pageable pageable);
}
