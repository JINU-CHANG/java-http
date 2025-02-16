package com.techcourse.controller;

import org.apache.catalina.controller.AbstractController;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class DefaultController extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        String responseBody = "Hello world!";

        response.createOKHttpResponse(response, responseBody);
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return false;
    }
}
