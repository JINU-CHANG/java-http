package com.techcourse.controller;

import org.apache.catalina.controller.AbstractController;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_LENGTH;
import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_TYPE;
import static org.apache.coyote.http11.common.HttpVersion.HTTP_VERSION11;
import static org.apache.coyote.http11.response.StatusCode.OK;

public class DefaultController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        String responseBody = "Hello world!";

        response.setHttpVersion(HTTP_VERSION11);
        response.setStatusCode(OK);
        response.setHeader(CONTENT_TYPE, "text/html;charset=utf-8");
        response.setHeader(CONTENT_LENGTH, String.valueOf(responseBody.length()));
        response.setResponseBody(responseBody);
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return false;
    }
}
