package com.techcourse.handler;

import org.apache.catalina.handler.Handler;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_LENGTH;
import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_TYPE;
import static org.apache.coyote.http11.common.HttpVersion.HTTP_VERSION11;
import static org.apache.coyote.http11.response.StatusCode.OK;

public class DefaultHandler implements Handler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String responseBody = "Hello world!";

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHttpVersion(HTTP_VERSION11);
        httpResponse.setStatusCode(OK);
        httpResponse.setHeader(CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.setHeader(CONTENT_LENGTH, String.valueOf(responseBody.length()));
        httpResponse.setResponseBody(responseBody);
        return httpResponse;
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return false;
    }
}
