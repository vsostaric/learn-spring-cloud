package org.locus.learn.gateway.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Data
@ConfigurationProperties
@PropertySource("classpath:auth.properties")
public class AuthProperties {

    private String serverSecret;
    private Integer serverInteger;

}
