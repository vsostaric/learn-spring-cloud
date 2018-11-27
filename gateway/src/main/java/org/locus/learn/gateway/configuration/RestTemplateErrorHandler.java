package org.locus.learn.gateway.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return isError(clientHttpResponse.getStatusCode());
    }

    private boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        logger.error("Response error: {} {}",
                clientHttpResponse.getStatusCode(), clientHttpResponse.getStatusText());
    }
}
