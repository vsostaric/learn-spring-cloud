package org.locus.learn.gatewayspring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootConfiguration
@EnableConfigurationProperties({ApiGatewayProperties.class})
public class GatewaySpringConfiguration {

    private ApiGatewayProperties apiGatewayProperties;

    @Autowired
    public GatewaySpringConfiguration(ApiGatewayProperties apiGatewayProperties) {
        this.apiGatewayProperties = apiGatewayProperties;
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {

        RouteLocatorBuilder.Builder routes = builder.routes();

        Function<GatewayFilterSpec, UriSpec> filter;
        apiGatewayProperties.getEndpoints().forEach(endpoint ->
                routes.route(p -> p
                        .path(endpoint.getPath())
                        .and()
                        .method(endpoint.getMethod().name())
                        .uri(endpoint.getLocation())
                )
        );

        return routes.build();
    }


}
