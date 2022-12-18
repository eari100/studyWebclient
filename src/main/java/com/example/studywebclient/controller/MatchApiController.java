package com.example.studywebclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
}
