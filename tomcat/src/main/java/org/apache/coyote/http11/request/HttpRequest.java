package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {

    private final RequestLine requestLine;
    private final RequestHeader requestHeader;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.requestLine = new RequestLine(bufferedReader);
        this.requestHeader = new RequestHeader(bufferedReader);
    }

    public String getMethod() {
       return this.requestLine.getMethod();
    }

    public String getURI() {
        return this.requestLine.getURI();
    }

    public String getParameter(String parameter) {
       return this.requestLine.getParameter(parameter);
    }

    public String getHeader(String header) {
        return this.requestHeader.getHeader(header);
    }
}
