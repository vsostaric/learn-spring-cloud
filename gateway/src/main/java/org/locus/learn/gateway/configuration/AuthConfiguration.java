package org.locus.learn.gateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan
//@EnableWebSecurity
@EnableConfigurationProperties({AuthProperties.class})
public class AuthConfiguration {

    private AuthProperties securityProperties;

    @Autowired
    public AuthConfiguration(AuthProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

}
