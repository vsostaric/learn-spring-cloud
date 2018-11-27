package org.locus.learn.gateway.transformer;

import org.apache.http.client.methods.RequestBuilder;

import javax.servlet.http.HttpServletRequest;

public interface ProxyRequestTransformer {

    RequestBuilder transform(RequestBuilder builder, HttpServletRequest request);

}
