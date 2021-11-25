package com.start.kalah.metrics;

import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MetricController {

    private final Random random = new Random();

    @GetMapping("metric")
    public String testMetric() {
        double metric = random.nextDouble() + 100;
        log.info("New Get metric: {} from {}", metric, this.getClass());
        return String.valueOf(metric);
    }

}
