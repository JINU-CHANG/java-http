package org.apache.catalina.controller;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public interface Controller {

    void service(HttpRequest request, HttpResponse response) throws Exception;
    boolean canHandle(HttpRequest httpRequest);
}
