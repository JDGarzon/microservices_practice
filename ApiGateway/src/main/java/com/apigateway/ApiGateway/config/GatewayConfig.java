package com.apigateway.ApiGateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apigateway.ApiGateway.filter.ResumenFilter;

@Configuration
public class GatewayConfig {

    @Autowired
    private ResumenFilter resumenFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("resumen_route", r -> r
                .path("/resumen")
                .filters(f -> f.filter(resumenFilter))
                .uri("http://dummy-uri"))
            .build();
    }
}
