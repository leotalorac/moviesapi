package com.endava.movies.moviesapi.model.entities;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class RatingEntity {
    private Integer userId;
    private Integer movieId;
    private Float rating;
    private ZonedDateTime timestamp;
}
