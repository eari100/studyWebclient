package com.example.studywebclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
public class MatchApiController {

    @Value("${riot.api.key}")
    String apiKey;

    @GetMapping("/v1/matches/{puuid}/ids")
    public List<String> getMatchesV1(@PathVariable String puuid, @RequestParam(defaultValue = "0") int start, @RequestParam(defaultValue = "20") int count) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Riot-Token", apiKey);

        return restTemplate.exchange(String.format("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/%s/ids?start=%s&count=%s", puuid, start, count),
                HttpMethod.GET, new HttpEntity(httpHeaders), List.class).getBody();
    }

    @GetMapping("/v2/matches/{puuid}/ids")
    public Mono<List> getMatchesV2(@PathVariable String puuid, @RequestParam(defaultValue = "0") int start, @RequestParam(defaultValue = "20") int count) {
        return WebClient.create(String.format("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/%s/ids?start=%s&count=%s", puuid, start, count))
                .get()
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .bodyToMono(List.class);
    }

    @GetMapping("/v1/matches/{matchId}")
    public Map<String, Object> getMatchDetailV1(@PathVariable String matchId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Riot-Token", apiKey);

        return restTemplate.exchange(String.format("https://asia.api.riotgames.com/lol/match/v5/matches/%s", matchId),
                HttpMethod.GET, new HttpEntity(httpHeaders), new ParameterizedTypeReference<Map>() {}).getBody();
    }
}
