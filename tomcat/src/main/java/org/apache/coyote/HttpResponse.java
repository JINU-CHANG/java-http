package org.apache.coyote;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.apache.coyote.StatusLine.HTTP_VERSION;
import static org.apache.coyote.StatusLine.STATUS_CODE;
import static org.apache.coyote.StatusLine.STATUS_MESSAGE;

public class HttpResponse {

    private static final String LINE_DELIMITER = "\r\n";
    private static final String DELIMITER = " ";

    private final Map<StatusLine, String> statusLine;
    private final Map<String, String> headers;
    private String responseBody;

    public HttpResponse () {
        this.statusLine = new EnumMap<>(StatusLine.class);
        this.headers = new LinkedHashMap<>();
        this.responseBody = "";
    }

    public void setStatusLine(StatusLine key, String value) {
        statusLine.put(key, value);
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setResponseBody(String value) {
        responseBody = value;
    }

    public String getStatusCode() {
        return statusLine.get(STATUS_CODE);
    }

    public String getHeader(String header) {
        return headers.get(header);
    }

    public String getHttpResponse() {
        StringBuilder responseBuilder = new StringBuilder();
        joinStatusLine(responseBuilder);
        joinHeader(responseBuilder);
        joinResponseBody(responseBuilder);
        return responseBuilder.toString();
    }

    private void joinStatusLine(StringBuilder responseBuilder) {
        responseBuilder.append(statusLine.get(HTTP_VERSION)).append(DELIMITER)
                .append(statusLine.get(STATUS_CODE)).append(DELIMITER)
                .append(statusLine.get(STATUS_MESSAGE)).append(DELIMITER)
                .append(LINE_DELIMITER);
    }

    private void joinHeader(StringBuilder responseBuilder) {
        for (Entry<String, String> keyValue : headers.entrySet()) {
            responseBuilder.append(keyValue.getKey()).append(": ")
                    .append(keyValue.getValue()).append(DELIMITER).append(LINE_DELIMITER);
        }
    }

    private void joinResponseBody(StringBuilder responseBuilder) {
        responseBuilder.append(LINE_DELIMITER)
                .append(responseBody);
    }
}
