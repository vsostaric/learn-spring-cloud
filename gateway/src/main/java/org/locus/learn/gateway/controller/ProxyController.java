package org.locus.learn.gateway.controller;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.locus.learn.gateway.transformer.ProxyRequestTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProxyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private HttpClient httpClient;

    private ProxyRequestTransformer requestTransformer;

    @Autowired
    public ProxyController(final ProxyRequestTransformer requestTransformer,
                           final HttpClient httpClient) {

        this.requestTransformer = requestTransformer;
        this.httpClient = httpClient;

    }

    @RequestMapping(value = "/**", method = {GET, POST, DELETE})
    @ResponseBody
    public ResponseEntity<String> proxyRequest(HttpServletRequest request) throws IOException {
        HttpUriRequest proxiedRequest = createHttpUriRequest(request);
        logger.info("request: {}", proxiedRequest);
        HttpResponse proxiedResponse = httpClient.execute(proxiedRequest);
        logger.info("Response {}", proxiedResponse.getStatusLine().getStatusCode());
        return new ResponseEntity<>(read(proxiedResponse.getEntity().getContent()), makeResponseHeaders(proxiedResponse), HttpStatus.valueOf(proxiedResponse.getStatusLine().getStatusCode()));
    }

    private HttpHeaders makeResponseHeaders(HttpResponse response) {
        HttpHeaders result = new HttpHeaders();
        Header h = response.getFirstHeader("Content-Type");
        result.set(h.getName(), h.getValue());
        return result;
    }

    private HttpUriRequest createHttpUriRequest(HttpServletRequest request) {
        RequestBuilder builder =
                requestTransformer.transform(RequestBuilder.create(request.getMethod()), request);
        return builder.build();
    }

    private String read(InputStream input) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(input))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

}
