package org.locus.learn.gateway.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "api.gateway")
public class ApiGatewayProperties {


    private List<Endpoint> endpoints;

    @Data
    public static class Endpoint {
        private String path;
        private RequestMethod method;
        private String location;

    }

}
