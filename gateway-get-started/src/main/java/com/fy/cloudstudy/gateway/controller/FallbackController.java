package com.fy.cloudstudy.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public Mono<String> fallback(){
        return Mono.just("fallback");
    }

    @RequestMapping("/resilience4j")
    public Mono<String> resilience4j(){
        return Mono.just("resilience4j");
    }
}
