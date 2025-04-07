package com.apigateway.ApiGateway.service;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Mono;

@Service
public class KeycloakAuthService {
     private final WebClient webClient;

    public KeycloakAuthService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    public Mono<String> login(String username, String password) {
    
        return webClient.post()
                .uri("/realms/gimnasio/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=password" +
                           "&client_id=equipo-service" +
                           "&client_secret=2Vj3y5xjtdPoy7z6ZLRmricChSUFuHEK" +
                           "&username=" + username +
                           "&password=" + password)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json.get("access_token").asText());
    }

}
