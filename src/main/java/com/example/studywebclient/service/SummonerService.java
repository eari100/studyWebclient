package com.example.studywebclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Transactional
@Service
public class SummonerService {

    @Value("${riot.api.key}")
    private String apiKey;

    public Map getSummonerV1(String summonerName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Riot-Token", apiKey);

        return restTemplate.exchange(String.format("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/%s", summonerName),
                HttpMethod.GET, new HttpEntity(httpHeaders), new ParameterizedTypeReference<Map>() {}).getBody();
    }

    public Mono getSummonerV2(String summonerName) {
        return WebClient.create(String.format("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/%s", summonerName))
                .get()
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
