package com.endava.movies.moviesapi.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO implements Serializable {
    private Integer id;
    private String originalTitle;
    private Boolean adult;
    private List<String> genres;
}
