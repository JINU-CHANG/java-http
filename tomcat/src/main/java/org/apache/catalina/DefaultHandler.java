package org.apache.catalina;

import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;

public class DefaultHandler implements Handler{

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String responseBody = "Hello world!";
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setValue("HTTP/1.1 200 OK ");
        httpResponse.setValue("Content-Type: text/html;charset=utf-8 ");
        httpResponse.setValue("Content-Length: " + responseBody.length() + " ");
        httpResponse.setValue("");
        httpResponse.setValue(responseBody);
        return httpResponse;
    }

    @Override
    public boolean canHandle(HttpRequest httpRequest) {
        return false;
    }
}
