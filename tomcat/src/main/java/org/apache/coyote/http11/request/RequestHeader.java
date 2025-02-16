package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.apache.coyote.http11.common.HttpHeaderName.COOKIE;

public class RequestHeader {

    private static final String HEADER_VALUE_DELIMITER = ": ";

    private final Map<String, String> header = new LinkedHashMap<>();

    public RequestHeader(BufferedReader bufferedReader) throws IOException {
        saveHeaders(bufferedReader);
    }

    private void saveHeaders(BufferedReader bufferedReader) throws IOException {
        String headerLine = "";
        while (!Objects.equals(headerLine = bufferedReader.readLine(), "") && headerLine != null) {
            String[] values = headerLine.split(HEADER_VALUE_DELIMITER);
            String headerName = values[0];
            String headerValue = values[1].trim();

            this.header.put(headerName, headerValue);
        }
    }

    public String getHeader(String name) {
        return this.header.get(name);
    }

    public Cookies getCookies() {
        String result = getHeader(COOKIE.name);
        if (result == null) return null;
        return new Cookies(result);
    }

    public String getCookieValue(String key) {
        if (getCookies() == null) return null;
        return getCookies().getValue(key);
    }
}
