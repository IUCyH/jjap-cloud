package com.iucyh.jjapcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {

    @GetMapping
    public String home() {
        return "hello";
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
