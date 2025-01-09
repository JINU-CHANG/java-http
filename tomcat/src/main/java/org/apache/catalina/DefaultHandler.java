package org.apache.catalina;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import static org.apache.coyote.http11.StatusLine.HTTP_VERSION;
import static org.apache.coyote.http11.StatusLine.STATUS_CODE;
import static org.apache.coyote.http11.StatusLine.STATUS_MESSAGE;

public class DefaultHandler implements Handler{

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String responseBody = "Hello world!";

        HttpResponse httpResponse = new HttpResponse();
        setStatusLine(httpResponse);
        setHeader(httpResponse, responseBody);
        setResponseBody(httpResponse, responseBody);
        return httpResponse;
    }

    private void setStatusLine(HttpResponse httpResponse) {
        httpResponse.setStatusLine(HTTP_VERSION, "HTTP/1.1");
        httpResponse.setStatusLine(STATUS_CODE, "200");
        httpResponse.setStatusLine(STATUS_MESSAGE, "OK");
    }

    private void setHeader(HttpResponse httpResponse, String value) {
        httpResponse.setHeader("Content-Type", "text/html;charset=utf-8");
        httpResponse.setHeader("Content-Length", String.valueOf(value.length()));
    }

    private void setResponseBody(HttpResponse httpResponse, String value) {
        httpResponse.setResponseBody(value);
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return false;
    }
}
