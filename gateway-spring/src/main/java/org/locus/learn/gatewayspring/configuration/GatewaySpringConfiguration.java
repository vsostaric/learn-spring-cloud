package org.locus.learn.gatewayspring.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

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
