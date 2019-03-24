package com.zeus.prometheus.controller;

import com.zeus.prometheus.beans.MyBean;
import com.zeus.prometheus.config.Config;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping(value = "/prometheus")
public class PrometheusController {

    @Autowired
    private MyBean myBean;

    @Autowired
    private Config config;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        System.out.println("using environment: " + config.getEnvironment());
        System.out.println("name: " + config.getName());
        System.out.println("servers: " + config.getServers());

        return "hello, prometheus." + myBean.getName();
    }


    final Counter request_total = Counter.build().name("request_total").labelNames("request_total").help("Total number of requests.").register();
    final Gauge request_rand = Gauge.build().name("request_rand").help("Rand number of request.").create().register();
    final Histogram requestLatency = Histogram.build().name("requests_latency_seconds").help("Request latency in seconds.").register();

    @RequestMapping(value = "/monitor", method = RequestMethod.GET)
    public String testMonitor() {

        request_total.labels("test_monitor").inc();
        System.out.println(request_total.labels("test_monitor").get());
        Random random = new Random();
        request_rand.set(random.nextInt());

        // Start the histogram timer
        Histogram.Timer requestTimer = requestLatency.startTimer();
        try {
            System.out.println("test...histogram");
        } finally {
            // Stop the histogram timer
            requestTimer.observeDuration();
        }
        return "success";
    }
}
