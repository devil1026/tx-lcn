package com.codingapi.txlcn.tracing;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Description:
 * Date: 19-1-28 下午4:35
 *
 * @author ujued
 */
@ConditionalOnClass(RestTemplate.class)
@Component
public class RestTemplateRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    @NonNull
    public ClientHttpResponse intercept(
            @NonNull HttpRequest httpRequest, @NonNull byte[] bytes,
            @NonNull ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().add(TracingConstants.HEADER_KEY_GROUP_ID, TracingContext.tracingContext().groupId());
        httpRequest.getHeaders().add(TracingConstants.HEADER_KEY_APP_LIST, TracingContext.tracingContext().appList());
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}