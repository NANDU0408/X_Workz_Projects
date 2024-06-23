//package com.xworkz.springproject.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//
//@RestController
//@RequestMapping("/api/location")
//public class LocationController {
//
//    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
//    private static final String API_URL = "https://www.universal-tutorial.com/api";
//    private static final String AUTH_TOKEN = "QUvcZFrRDpIskuZi0hIoCPGCTaxbJ1sLsyBfMbrWa8FNaGM7S3OHoOaFbW98RpPGh6c";
//
//    private final RestTemplate restTemplate;
//
//    public LocationController(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @GetMapping("/countries")
//    public List<Map<String, String>> getCountries() {
//        String url = API_URL + "/countries";
//        return makeGetRequest(url);
//    }
//
//    @GetMapping("/states")
//    public List<Map<String, String>> getStates(@RequestParam String country) {
//        String url = API_URL + "/states/" + country;
//        return makeGetRequest(url);
//    }
//
//    @GetMapping("/cities")
//    public List<Map<String, String>> getCities(@RequestParam String state) {
//        String url = API_URL + "/cities/" + state;
//        return makeGetRequest(url);
//    }
//
//    private List<Map<String, String>> makeGetRequest(String url) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(AUTH_TOKEN);
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        // Log the request URL
//        logger.info("Making GET request to URL: {}", url);
//
//        ResponseEntity<List<Map<String, String>>> response = restTemplate.exchange(
//                url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Map<String, String>>>() {}
//        );
//
//        // Log the response body
//        logger.info("Response received: {}", response.getBody());
//
//        return response.getBody();
//    }
//}
