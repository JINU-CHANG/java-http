package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.requestLine = new RequestLine(bufferedReader);
        this.requestHeader = new RequestHeader(bufferedReader);

        if (getMethod().equals("POST")) {
            int contentLength = Integer.parseInt(getHeader("Content-Length"));
            this.requestBody = new RequestBody(bufferedReader, contentLength);
        }
    }

    public String getMethod() {
       return requestLine.getMethod();
    }

    public String getURI() {
        return requestLine.getURI();
    }

    public String getParameter(String parameter) {
       return requestLine.getParameter(parameter);
    }

    public String getHeader(String header) {
        return requestHeader.getHeader(header);
    }

    public String getRequestBody() {
        return requestBody.getRequestBody();
    }

    public void setURI(String uri) {
        requestLine.setURI(uri);
    }
}
