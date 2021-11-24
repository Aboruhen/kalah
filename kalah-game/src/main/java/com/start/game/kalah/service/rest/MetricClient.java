package com.start.game.kalah.service.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("kalah-metrics")
public interface MetricClient {

    @GetMapping("metric")
    String metric();

}
