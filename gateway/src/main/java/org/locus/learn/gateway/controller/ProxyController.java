package org.locus.learn.gateway.controller;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.locus.learn.gateway.transformer.ProxyRequestTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProxyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestTemplate restTemplate;

    private ProxyRequestTransformer requestTransformer;

    @Autowired
    public ProxyController(final ProxyRequestTransformer requestTransformer,
                           final RestTemplate restTemplate) {

        this.requestTransformer = requestTransformer;
        this.restTemplate = restTemplate;

    }

    @RequestMapping(value = "/**", method = {GET, POST, DELETE})
    @ResponseBody
    public ResponseEntity<String> proxyRequest(HttpServletRequest request) {
        HttpUriRequest proxiedRequest = createHttpUriRequest(request);
        logger.info("request: {}", proxiedRequest);
        ResponseEntity<String> response = restTemplate.getForEntity(proxiedRequest.getURI(), String.class);
        logger.info("Response {}", response.getStatusCode());
        return new ResponseEntity<>(response.getBody(), response.getHeaders(), response.getStatusCode());
    }


    private HttpUriRequest createHttpUriRequest(HttpServletRequest request) {
        RequestBuilder builder =
                requestTransformer.transform(RequestBuilder.create(request.getMethod()), request);
        return builder.build();
    }

}
