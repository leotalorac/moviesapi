package com.endava.movies.moviesapi.service.impl;

import com.endava.movies.moviesapi.model.entities.GenreEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
public class GenreParser {
    public List<String> parseJson(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        List<GenreEntity> genres= mapper.readValue(jsonString, new TypeReference<ArrayList<GenreEntity>>() {});
        return genres.stream().map(genre -> genre.getName()).collect(Collectors.toList());
    }
}
