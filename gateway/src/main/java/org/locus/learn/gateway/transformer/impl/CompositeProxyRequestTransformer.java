package org.locus.learn.gateway.transformer.impl;

import org.apache.http.client.methods.RequestBuilder;
import org.locus.learn.gateway.transformer.ProxyRequestTransformer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CompositeProxyRequestTransformer implements ProxyRequestTransformer {

    private List<ProxyRequestTransformer> transformers;

    public CompositeProxyRequestTransformer(List<ProxyRequestTransformer> transformers) {
        this.transformers = transformers;
    }

    @Override
    public RequestBuilder transform(RequestBuilder builder, HttpServletRequest request) {

        for (ProxyRequestTransformer transformer : transformers) {
            builder = transformer.transform(builder, request);
        }

        return builder;
    }
}
