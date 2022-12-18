package com.example.studywebclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
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
}
