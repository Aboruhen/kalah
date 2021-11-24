package com.start.kalah.metrics;

import java.util.Random;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricController {

    private final Random random = new Random();

    @GetMapping("metric")
    public String testMetric() {
        return String.valueOf(random.nextDouble());
    }

}
