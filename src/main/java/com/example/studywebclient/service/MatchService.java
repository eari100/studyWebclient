package com.example.studywebclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@Service
public class MatchService {

    @Value("${riot.api.key}")
    String apiKey;

    public Flux getMatchListDetailV2(List<String> matchIdList) {
        return Flux.fromIterable(matchIdList)
                .flatMap(this::getMatchDetailV2);
    }

    public Mono<Map> getMatchDetailV2(String matchId) {
        log.info(String.format("Calling getMatchDetailV2 matchId = %s", matchId));

        long start = System.currentTimeMillis();
        Mono<Map> result = WebClient.create(String.format("https://asia.api.riotgames.com/lol/match/v5/matches/%s", matchId))
                .get()
                .header("X-Riot-Token", apiKey)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});

        long end = System.currentTimeMillis();
        long secDiffTime = (end - start) / 1000;
        log.info(String.format("시간차이(m) : %s", secDiffTime));

        return result;
    }
}
