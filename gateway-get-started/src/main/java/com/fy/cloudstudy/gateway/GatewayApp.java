package com.fy.cloudstudy.gateway;

import com.fy.cloudstudy.gateway.config.UriConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(UriConfiguration.class)
@SpringBootApplication
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder, UriConfiguration uriConfiguration){
        String httpUri = uriConfiguration.getHttpbin();
        return routeLocatorBuilder.routes()
//                .route(p ->
//                        p.path("/get")
//                    .filters(f ->
//                        f.addRequestHeader("Hello", "World"))
//                    .uri(httpUri))
//                .route(p -> p
//                    .host("*.hystrix.com")
//                    .filters(f -> f.hystrix(config -> config.setName("mycmd").setFallbackUri("forward:/fallback")))
//                    .uri(httpUri))
                .build();
    }
}
