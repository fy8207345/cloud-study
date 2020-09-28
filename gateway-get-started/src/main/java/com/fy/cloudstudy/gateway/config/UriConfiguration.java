package com.fy.cloudstudy.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties
public class UriConfiguration {
    private String httpbin = "http://httpbin.org:80";
}
