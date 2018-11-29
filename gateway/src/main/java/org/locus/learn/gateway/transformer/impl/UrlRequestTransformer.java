package org.locus.learn.gateway.transformer.impl;

import org.apache.http.client.methods.RequestBuilder;
import org.locus.learn.gateway.model.ApiGatewayProperties;
import org.locus.learn.gateway.transformer.ProxyRequestTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlRequestTransformer implements ProxyRequestTransformer {

    private ApiGatewayProperties apiGatewayProperties;

    public UrlRequestTransformer(ApiGatewayProperties apiGatewayProperties) {
        this.apiGatewayProperties = apiGatewayProperties;
    }

    @Override
    public RequestBuilder transform(RequestBuilder builder, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String serviceUrl = getServiceUrl(requestURI, request);

        if (!StringUtils.isEmpty(request.getQueryString())) {
            serviceUrl = serviceUrl + "?" + request.getQueryString();
        }

        URI uri = null;
        try {
            uri = new URI(serviceUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }

        if (uri == null) {
            return null;
        }

        builder.setUri(uri);
        return builder;

    }

    private String getServiceUrl(String requestURI, HttpServletRequest httpServletRequest) {

        ApiGatewayProperties.Endpoint endpoint =
                apiGatewayProperties.getEndpoints().stream()
                        .filter(e ->
                                requestURI.matches(e.getPath()) && e.getMethod() == RequestMethod.valueOf(httpServletRequest.getMethod())
                        )
                        .findFirst().orElseGet(null);
        return endpoint.getLocation() + requestURI;
    }
}
