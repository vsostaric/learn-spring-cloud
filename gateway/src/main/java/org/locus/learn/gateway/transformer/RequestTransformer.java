package org.locus.learn.gateway.transformer;

import com.google.common.collect.Lists;
import org.apache.http.client.methods.RequestBuilder;
import org.locus.learn.gateway.model.ApiGatewayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class RequestTransformer implements ProxyRequestTransformer {

    private ApiGatewayProperties apiGatewayProperties;

    private CompositeProxyRequestTransformer transformers;

    @Autowired
    public RequestTransformer(ApiGatewayProperties apiGatewayProperties) {
        this.apiGatewayProperties = apiGatewayProperties;
        this.transformers =
                new CompositeProxyRequestTransformer(
                        Lists.newArrayList(
                                new UrlRequestTransformer(this.apiGatewayProperties),
                                new ContentRequestTransformer()
                        ));
    }

    @Override
    public RequestBuilder transform(RequestBuilder builder, HttpServletRequest request) {
        return this.transformers.transform(builder, request);
    }
}
