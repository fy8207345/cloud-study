package com.fy.cloudstudy.gateway.config;

import com.fy.cloudstudy.gateway.filters.CustomGlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

    @Bean
    public CustomGlobalFilter customGlobalFilter(){
        return new CustomGlobalFilter();
    }
}
