package com.apigateway.ApiGateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResumenFilter implements GatewayFilter {

    private final WebClient.Builder webClientBuilder;

    public ResumenFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        WebClient client = webClientBuilder.build();

        Mono<List<Object>> miembrosMono = client.get()
                .uri("http://miembro-service/all")
                .headers(headers -> copyAuthHeader(exchange, headers))
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList();

        Mono<List<Object>> entrenadoresMono = client.get()
                .uri("http://entrenador-service/all")
                .headers(headers -> copyAuthHeader(exchange, headers))
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList();

        Mono<List<Object>> clasesMono = client.get()
                .uri("http://clase-service/all")
                .headers(headers -> copyAuthHeader(exchange, headers))
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList();

        Mono<List<Object>> equiposMono = client.get()
                .uri("http://equipo-service/all")
                .headers(headers -> copyAuthHeader(exchange, headers))
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList();

        return Mono.zip(miembrosMono, entrenadoresMono, clasesMono, equiposMono)
                .flatMap(tuple -> {
                    List<Object> miembros = tuple.getT1();
                    List<Object> entrenadores = tuple.getT2();
                    List<Object> clases = tuple.getT3();
                    List<Object> equipos = tuple.getT4();

                    Map<String, Object> response = new LinkedHashMap<>();
                    response.put("miembros", miembros);
                    response.put("entrenadores", entrenadores);
                    response.put("clases", clases);
                    response.put("equipos", equipos);

                    Map<String, Object> resumen = new LinkedHashMap<>();
                    resumen.put("totalMiembros", miembros.size());
                    resumen.put("totalEntrenadores", entrenadores.size());
                    resumen.put("totalClases", clases.size());
                    resumen.put("totalEquipos", equipos.size());

                    response.put("resumen", resumen);

                    byte[] responseBytes;
                    try {
                        responseBytes = new ObjectMapper().writeValueAsBytes(response);
                    } catch (JsonProcessingException e) {
                        return Mono.error(e);
                    }

                    ServerHttpResponse serverResponse = exchange.getResponse();
                    serverResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBuffer buffer = serverResponse.bufferFactory().wrap(responseBytes);
                    return serverResponse.writeWith(Mono.just(buffer));
                });
    }

    // Método auxiliar para copiar el header Authorization del request original
    private void copyAuthHeader(ServerWebExchange exchange, HttpHeaders headers) {
        String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth != null) {
            headers.set(HttpHeaders.AUTHORIZATION, auth);
        }
    }
}
