package com.fy.cloudstudy.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.factory.FallbackHeadersGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerFilterFactory;
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerResilience4JFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.DispatcherHandler;

import java.time.Duration;

@Configuration
public class CircuitBreakerConfiguration {

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> myCircuitBreaker(){
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
        .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
        .timeLimiterConfig(TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(20)) // 超过duration未响应，直接fallback
                .build()).build());
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringCloudCircuitBreakerFilterFactory cloudCircuitBreakerFilterFactory(ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory,
                                                                                   ObjectProvider<DispatcherHandler> dispatcherHandlers){
        return new SpringCloudCircuitBreakerResilience4JFilterFactory(reactiveResilience4JCircuitBreakerFactory, dispatcherHandlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public FallbackHeadersGatewayFilterFactory fallbackHeadersGatewayFilterFactory(){
        return new FallbackHeadersGatewayFilterFactory();
    }
}
