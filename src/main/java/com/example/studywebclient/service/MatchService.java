package com.example.studywebclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Transactional
@Service
public class MatchService {

    @Value("${riot.api.key}")
    String apiKey;

    public Mono<Map> getMatchDetailV2(String matchId) {
        log.info(String.format("Calling getMatchDetailV2 matchId = %s", matchId));

        return WebClient.create(String.format("https://asia.api.riotgames.com/lol/match/v5/matches/%s", matchId))
                .get()
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
