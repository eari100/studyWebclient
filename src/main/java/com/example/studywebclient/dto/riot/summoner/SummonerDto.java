package com.example.studywebclient.dto.riot.summoner;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SummonerDto {
    private String id;
    private String accountId;
    private int profileIconId;
    private long revisionDate;
    private String name;
    private String puuid;
    private long summonerLevel;
}
