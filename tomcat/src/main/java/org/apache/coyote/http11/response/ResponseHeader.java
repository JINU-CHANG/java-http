package org.apache.coyote.http11.response;

import org.apache.coyote.http11.common.HttpHeaderName;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseHeader {

    private final Map<HttpHeaderName, String> headers;

    public ResponseHeader() {
        this.headers = new LinkedHashMap<>();
    }

    public void setHeader(HttpHeaderName key, String value) {
        headers.put(key, value);
    }

    public String getHeader(HttpHeaderName header) {
        return headers.get(header);
    }

    public Map<HttpHeaderName, String> getHeaders() {
        return headers;
    }
}
