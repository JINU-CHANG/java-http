package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;

public class RequestBody {

    private final String requestBody;

    public RequestBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        char[] buffer = new char[contentLength];
        bufferedReader.read(buffer, 0, contentLength);
        this.requestBody = new String(buffer).trim();
    }

    public String getRequestBody() {
        return requestBody;
    }
}
