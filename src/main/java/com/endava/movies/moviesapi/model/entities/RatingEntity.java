package com.endava.movies.moviesapi.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "ratings")
public class RatingEntity {
    @Id
    @Field("_id")
    private Integer id;
    private Integer userId;
    private Integer movieId;
    private Float rating;
    private String timestamp;
}
