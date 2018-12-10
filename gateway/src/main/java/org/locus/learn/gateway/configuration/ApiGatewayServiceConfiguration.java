package org.locus.learn.gateway.configuration;

import com.google.common.collect.Lists;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.locus.learn.gateway.model.ApiGatewayProperties;
import org.locus.learn.gateway.transformer.ProxyRequestTransformer;
import org.locus.learn.gateway.transformer.impl.CompositeProxyRequestTransformer;
import org.locus.learn.gateway.transformer.impl.ContentRequestTransformer;
import org.locus.learn.gateway.transformer.impl.UrlRequestTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

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
    public HttpClient httpClient() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        return HttpClients.custom()
                .setConnectionManager(connManager)
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ProxyRequestTransformer proxyRequestTransformer() {
        return new CompositeProxyRequestTransformer(
                Lists.newArrayList(
                        new UrlRequestTransformer(this.apiGatewayProperties),
                        new ContentRequestTransformer()
                ));
    }

}
