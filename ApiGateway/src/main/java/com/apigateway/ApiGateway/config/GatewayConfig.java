package com.apigateway.ApiGateway.config;

import com.apigateway.ApiGateway.filter.MemberSummaryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                .uri("http://dummy-uri")) // URI dummy, no se usa
            .build();
    }
}
