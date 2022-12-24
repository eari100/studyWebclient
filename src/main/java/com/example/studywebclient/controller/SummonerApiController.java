package com.example.studywebclient.controller;

import com.example.studywebclient.dto.riot.SummonerDto;
import com.example.studywebclient.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class SummonerApiController {

    private final SummonerService summonerService;

    @GetMapping("/v1/summoners/{summonerName}")
    public SummonerDto getSummonerV1(@PathVariable String summonerName) {
        return summonerService.getSummonerV1(summonerName);
    }

    @GetMapping("/v2/summoners/{summonerName}")
    public Mono getSummonerV2(@PathVariable String summonerName) {
        return summonerService.getSummonerV2(summonerName);
    }
}
