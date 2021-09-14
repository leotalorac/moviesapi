package com.endava.movies.moviesapi.controller;

import com.endava.movies.moviesapi.service.MovieService;
import com.endava.movies.moviesapi.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(MoviesApiController.class)
public class MoviesApiControllerTest {

    private final String URL = "/v1/demo-service";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieService movieServiceImpl;
    @MockBean
    private RatingService ratingServiceImpl;

    @Test
    void healthCheck() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(URL+"/health-check");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("OK",result.getResponse().getContentAsString());
        assertEquals(200,result.getResponse().getStatus());
    }

    @Test
    void movie_finding_noContent() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(URL+"/movie/0");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("",result.getResponse().getContentAsString());
        assertEquals(204,result.getResponse().getStatus());
    }

    @Test
    void ratings_finding_noContent() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get(URL+"/movie/0");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("",result.getResponse().getContentAsString());
        assertEquals(204,result.getResponse().getStatus());
    }

    @Test
    void movies_upload_success() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post(URL+"/load/movies");
        when(movieServiceImpl.saveMovies()).thenReturn(10);
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("Movies uploaded",result.getResponse().getContentAsString());
        assertEquals(200,result.getResponse().getStatus());
    }

    @Test
    void ratings_upload_success() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post(URL+"/load/ratings");
        when(ratingServiceImpl.saveRatings()).thenReturn(10);
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("Ratings Uploaded",result.getResponse().getContentAsString());
        assertEquals(200,result.getResponse().getStatus());
    }

}
