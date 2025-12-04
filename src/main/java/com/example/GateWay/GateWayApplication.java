package com.example.GateWay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GateWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GateWayApplication.class, args);
	}

	@Bean
	DiscoveryClientRouteDefinitionLocator routesDynamique(
			ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
	}

	@Bean
	RouteLocator customRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("clients-route", r -> r.path("/clients/**")
						.uri("lb://Client"))
				.route("service-client-uppercase-route", r -> r.path("/SERVICE-CLIENT/**")
						.filters(f -> f.stripPrefix(1))
						.uri("lb://Client"))
				.route("service-client-route", r -> r.path("/Client/**")
						.filters(f -> f.stripPrefix(1))
						.uri("lb://Client"))
				.route("voitures-route", r -> r.path("/voitures/**")
						.uri("http://localhost:8089"))
				.build();
	}

}
