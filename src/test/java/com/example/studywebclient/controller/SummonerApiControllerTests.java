package com.example.studywebclient.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SummonerApiController.class)
public class SummonerApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getSummonerV1_테스트() throws Exception {
        final String summonerName = "한남동의 황제";

        long beforeTime = System.currentTimeMillis();

        mockMvc.perform(get(String.format("/v1/summoners/%s", summonerName)))
                .andExpect(status().isOk())
                .andDo(print());

        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        System.out.println(String.format("시간차이(m) : %s", secDiffTime));
    }
}
