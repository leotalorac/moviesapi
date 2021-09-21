package com.endava.movies.moviesapi.model.dto;

import com.endava.movies.moviesapi.model.entities.GenreEntity;
import com.endava.movies.moviesapi.model.entities.RatingEntity;
import lombok.*;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieDTO implements Serializable {
    private Integer id;
    private String originalTitle;
    private Boolean adult;
    private List<String> genres;
    private String overview;
    private String originalLanguage;
    private Float rating;
    private Integer budget;
    private String title;
    private String image;
}
