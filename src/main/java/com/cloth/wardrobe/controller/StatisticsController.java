package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.dto.statistics.ResponseForStatistics;
import com.cloth.wardrobe.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("api/v1/statistics")
    public ResponseEntity<ResponseForStatistics> findStatistics() {
        return statisticsService.findStatistics(Optional.empty());
    }

    @GetMapping("api/v1/statistics/me")
    public ResponseEntity<ResponseForStatistics> findStatisticsByMember(@LoginUser SessionMember sessionMember) {
        return statisticsService.findStatistics(Optional.ofNullable(sessionMember));
    }

}
