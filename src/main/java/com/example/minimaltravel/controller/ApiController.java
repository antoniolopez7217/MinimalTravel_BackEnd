package com.example.minimaltravel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api", "/api/"})
public class ApiController {

    @GetMapping
    public String hello() {
        return "API funcionando";
    }
}