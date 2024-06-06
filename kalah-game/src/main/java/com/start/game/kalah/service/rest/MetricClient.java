package com.start.game.kalah.service.rest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "kalah-metrics", url = "kalah-metrics:8080")
@Component
public interface MetricClient {

    @GetMapping("metric")
    String metric();

}
