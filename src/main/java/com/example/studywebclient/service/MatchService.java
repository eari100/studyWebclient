package com.example.studywebclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
@Service
public class MatchService {

    @Value("${riot.api.key}")
    String apiKey;

    public List<Map> getMatchListDetailV1(List<String> matchIdList) {
        List<Map> result = new ArrayList<>();

        matchIdList.forEach(matchId -> result.add(getMatchDetailV1(matchId)));

        return result;
    }

    public Map getMatchDetailV1(String matchId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Riot-Token", apiKey);

        return restTemplate.exchange(String.format("https://asia.api.riotgames.com/lol/match/v5/matches/%s", matchId),
                HttpMethod.GET, new HttpEntity(httpHeaders), new ParameterizedTypeReference<Map>() {}).getBody();
    }

    public ParallelFlux<Map> getMatchListDetailV2(List<String> matchIdList) {
        return Flux.fromIterable(matchIdList)
                .parallel()
                .runOn(Schedulers.boundedElastic())
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
