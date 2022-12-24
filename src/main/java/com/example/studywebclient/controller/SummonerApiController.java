package com.example.studywebclient.controller;

import com.example.studywebclient.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class SummonerApiController {

    @Value("${riot.api.key}")
    private String apiKey;

    private final SummonerService summonerService;

    @GetMapping("/v1/summoners/{summonerName}")
    public Map<String, Object> getSummonerV1(@PathVariable String summonerName) {
        return summonerService.getSummonerV1(summonerName);
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
