package org.apache.coyote.http11.request;

import org.apache.coyote.http11.common.HttpMethodName;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.apache.coyote.http11.common.HttpHeaderName.CONTENT_LENGTH;
import static org.apache.coyote.http11.common.HttpMethodName.POST;

public class HttpRequest {
    
    private final RequestLine requestLine;
    private final RequestHeader requestHeader;
    private RequestBody requestBody;

    public HttpRequest(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            this.requestLine = new RequestLine(bufferedReader);
            this.requestHeader = new RequestHeader(bufferedReader);

            if (isRequestBodyExist()) {
                int contentLength = Integer.parseInt(getHeader(CONTENT_LENGTH.name));
                this.requestBody = new RequestBody(bufferedReader, contentLength);
            }
        } catch (IOException ioException) {
            throw new IllegalArgumentException("HTTP 요청이 잘못되었습니다.", ioException);
        }
    }

    private boolean isRequestBodyExist() {
        return getMethod().equals(POST) && getHeader(CONTENT_LENGTH.name) != null;
    }

    public HttpMethodName getMethod() {
       return requestLine.getMethod();
    }

    public String getUri() {
        return requestLine.getUri();
    }

    public String getParameter(String parameter) {
       return requestLine.getParameter(parameter);
    }

    public String getHeader(String name) {
        return requestHeader.getHeader(name);
    }

    public String getRequestBody() {
        return requestBody.getRequestBody();
    }

    public String getCookieValue(String key) {
        return requestHeader.getCookieValue(key);
    }

    public void setUri(String uri) {
        requestLine.setUri(uri);
    }
}
