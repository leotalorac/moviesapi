package com.endava.movies.moviesapi.model.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Document(collection = "movies")
public class MovieEntity implements Serializable {
    @Id
    @Field("_id")
    private Integer id;
    @Field("original_title")
    private String originalTitle;
    private Boolean adult;
    private List<String> genres;
}
