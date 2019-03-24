package com.zeus.prometheus;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableSpringBootMetricsCollector
@EnablePrometheusEndpoint
public class PrometheusApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrometheusApplication.class, args);
	}

}
