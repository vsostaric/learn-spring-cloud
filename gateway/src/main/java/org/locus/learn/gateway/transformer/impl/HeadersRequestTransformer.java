package org.locus.learn.gateway.transformer.impl;

import org.apache.http.client.methods.RequestBuilder;
import org.locus.learn.gateway.transformer.ProxyRequestTransformer;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class HeadersRequestTransformer implements ProxyRequestTransformer {

    @Override
    public RequestBuilder transform(RequestBuilder builder, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            if (headerName.equals("x-access-token")) {
                builder.addHeader(headerName, headerValue);
            }
        }

        return builder;
    }
}
