package org.locus.learn.gateway.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.locus.learn.gateway.model.ApiGatewayProperties;
import org.locus.learn.gateway.transformer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootConfiguration
@Import({AuthConfiguration.class})
@EnableConfigurationProperties({ApiGatewayProperties.class})
public class ApiGatewayServiceConfiguration {

    private ApiGatewayProperties apiGatewayProperties;

    @Autowired
    public ApiGatewayServiceConfiguration(ApiGatewayProperties apiGatewayProperties) {
        this.apiGatewayProperties = apiGatewayProperties;
    }

    @Bean
    public RestTemplate restTemplate(HttpMessageConverters converters) {

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/json"));
        converter.setObjectMapper(new ObjectMapper());

        HttpClient httpClient = HttpClients.createDefault();
        RestTemplate restTemplate = new RestTemplate(Collections.<HttpMessageConverter<?>>singletonList(converter));
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        return restTemplate;
    }

}
