package com.endava.movies.moviesapi.controller;

import com.endava.movies.moviesapi.service.MovieService;
import com.endava.movies.moviesapi.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(DemoController.class)
public class DemoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovieService movieServiceImpl;
    @MockBean
    private RatingService ratingServiceImpl;

    @Test
    void healthCheck() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/v1/demo-service/health-check");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("OK",result.getResponse().getContentAsString());
    }


}
