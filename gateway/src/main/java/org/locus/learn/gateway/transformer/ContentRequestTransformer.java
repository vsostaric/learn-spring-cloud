package org.locus.learn.gateway.transformer;

import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

public class ContentRequestTransformer implements ProxyRequestTransformer {


    @Override
    public RequestBuilder transform(RequestBuilder builder, HttpServletRequest request) {

        String requestContent = null;
        try {
            requestContent = request.getReader().lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!requestContent.isEmpty()) {
            StringEntity entity = new StringEntity(requestContent, ContentType.APPLICATION_JSON);
            builder.setEntity(entity);
        }

        return builder;
    }
}
