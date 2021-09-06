package com.endava.movies.moviesapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/")
    public ResponseEntity<String> status(){
        return ResponseEntity.ok("OK");
    }
}
