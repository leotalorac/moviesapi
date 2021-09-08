package com.endava.movies.moviesapi.model.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "movies")
public class MovieEntity implements Serializable {
    @Id
    @Field("_id")
    private Integer id;
    @Field("original_title")
    private String originalTitle;
    private Boolean adult;
    private List<GenreEntity> genres;
    private String overview;
    private String originalLanguage;
    private List<RatingEntity> ratings;
    private Integer budget;
    private String title;
}
