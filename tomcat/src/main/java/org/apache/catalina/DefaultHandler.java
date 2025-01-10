package org.apache.catalina;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import static org.apache.coyote.http11.HttpHeaderName.CONTENT_LENGTH;
import static org.apache.coyote.http11.HttpHeaderName.CONTENT_TYPE;
import static org.apache.coyote.http11.response.StatusCode.OK;

public class DefaultHandler implements Handler{

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String responseBody = "Hello world!";

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHttpVersion("HTTP/1.1");
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
