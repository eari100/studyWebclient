package com.example.studywebclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class SummonerApiController {

    @Value("${riot.api.key}")
    String apiKey;

    @GetMapping("/v1/summoners/{summonerName}")
    public Map<String, Object> getSummonerV1(@PathVariable String summonerName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Riot-Token", apiKey);

        return restTemplate.exchange(String.format("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/%s", summonerName),
                HttpMethod.GET, new HttpEntity(httpHeaders), new ParameterizedTypeReference<Map>() {}).getBody();
    }

    @GetMapping("/v2/summoners/{summonerName}")
    public Mono<Map> getSummonerV2(@PathVariable String summonerName) {
        return WebClient.create(String.format("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/%s", summonerName))
                .get()
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
