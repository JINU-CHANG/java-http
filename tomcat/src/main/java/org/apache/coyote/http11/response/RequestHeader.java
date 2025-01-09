package org.apache.coyote.http11.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestHeader {

    private final Map<String, String> headers;

    public RequestHeader() {
        this.headers = new LinkedHashMap<>();
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
