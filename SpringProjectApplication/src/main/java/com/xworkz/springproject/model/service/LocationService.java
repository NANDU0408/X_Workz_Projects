//package com.xworkz.springproject.model.service;
//
//import com.google.api.client.util.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Service
//public class LocationService {
//
//    private final WebClient webClient;
//
//    @Value("${api.base.url}")
//    private String baseUrl;
//
//    @Value("${api.auth.token}")
//    private String authToken;
//
//    public LocationService(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
//    }
//
//    public Mono<String> getCountries() {
//        return this.webClient.get()
//                .uri("/countries")
//                .header("Authorization", "Bearer " + authToken)
//                .header("Accept", "application/json")
//                .retrieve()
//                .bodyToMono(String.class);
//    }
//
//    public Mono<String> getStatesByCountry(String countryName) {
//        return this.webClient.get()
//                .uri("/states/" + countryName)
//                .header("Authorization", "Bearer " + authToken)
//                .header("Accept", "application/json")
//                .retrieve()
//                .bodyToMono(String.class);
//    }
//
//    public Mono<String> getCitiesByState(String stateName) {
//        return this.webClient.get()
//                .uri("/cities/" + stateName)
//                .header("Authorization", "Bearer " + authToken)
//                .header("Accept", "application/json")
//                .retrieve()
//                .bodyToMono(String.class);
//    }
//}
