package com.example.studywebclient.controller;

import com.example.studywebclient.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import reactor.core.publisher.ParallelFlux;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MatchApiController {

    @Value("${riot.api.key}")
    String apiKey;

    private final MatchService matchService;

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
        return matchService.getMatchDetailV1(matchId);
    }

    @GetMapping("/v2/matches/{matchId}")
    public Mono<Map> getMatchDetailV2(@PathVariable String matchId) {
        return matchService.getMatchDetailV2(matchId);
    }
    @GetMapping("/v1/matches")
    public List<Map> getMatchDetailListV1() {
        List<String> matchIdList = Arrays.asList(
                "KR_6272198808","KR_6272185507","KR_6272161179", "KR_6272116818","KR_6269395293",
                "KR_6269377935", "KR_6269369655","KR_6269347824","KR_6269313597", "KR_6269281624",
                "KR_6269257241","KR_6269214800","KR_6269185300","KR_6269124157","KR_6269032458",
                "KR_6268966927","KR_6268912017","KR_6268833166","KR_6255975855","KR_6255883753");

        long start = System.currentTimeMillis();
        List<Map> result = matchService.getMatchListDetailV1(matchIdList);
        long end = System.currentTimeMillis();
        long secDiffTime = (end - start) / 1000;
        System.out.println(String.format("시간차이(m) : %s", secDiffTime));

        return result;
    }

    @GetMapping("/v2/matches")
    public ParallelFlux<Map> getMatchDetailListV2() {
        List<String> matchIdList = Arrays.asList(
                "KR_6272198808","KR_6272185507","KR_6272161179", "KR_6272116818","KR_6269395293",
                "KR_6269377935", "KR_6269369655","KR_6269347824","KR_6269313597", "KR_6269281624",
                "KR_6269257241","KR_6269214800","KR_6269185300","KR_6269124157","KR_6269032458",
                "KR_6268966927","KR_6268912017","KR_6268833166","KR_6255975855","KR_6255883753");

        long start = System.currentTimeMillis();
        ParallelFlux<Map> result = matchService.getMatchListDetailV2(matchIdList);
        long end = System.currentTimeMillis();
        long secDiffTime = (end - start) / 1000;
        System.out.println(String.format("시간차이(m) : %s", secDiffTime));

        return result;
    }
}
