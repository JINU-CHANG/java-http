package org.apache.coyote.http11.response;

import java.util.Map.Entry;

public class HttpResponse {

    private static final String LINE_DELIMITER = "\r\n";
    private static final String DELIMITER = " ";

    private final StatusLine statusLine;
    private final ResponseHeader requestHeader;
    private String responseBody;

    public HttpResponse () {
        this.statusLine = new StatusLine();
        this.requestHeader = new ResponseHeader();
    }

    public void setHttpVersion(String httpVersion) {
        statusLine.setHttpVersion(httpVersion);
    }

    public void setStatusCode(StatusCode statusCode) {
        statusLine.setStatusCode(statusCode);
    }

    public void setHeader(String key, String value) {
        requestHeader.setHeader(key, value);
    }

    public void setResponseBody(String value) {
        responseBody = value;
    }

    public int getStatusCode() {
        return statusLine.getStatusCode();
    }

    public String getHeader(String header) {
        return requestHeader.getHeader(header);
    }

    public String getHttpResponse() {
        StringBuilder responseBuilder = new StringBuilder();
        joinStatusLine(responseBuilder);
        joinHeader(responseBuilder);
        joinResponseBody(responseBuilder);
        return responseBuilder.toString();
    }

    private void joinStatusLine(StringBuilder responseBuilder) {
        responseBuilder.append(statusLine.getHttpVersion()).append(DELIMITER)
                .append(statusLine.getStatusCode()).append(DELIMITER)
                .append(statusLine.getStatusMessage()).append(DELIMITER)
                .append(LINE_DELIMITER);
    }

    private void joinHeader(StringBuilder responseBuilder) {
        for (Entry<String, String> keyValue : requestHeader.getHeaders().entrySet()) {
            responseBuilder.append(keyValue.getKey()).append(": ")
                    .append(keyValue.getValue()).append(DELIMITER).append(LINE_DELIMITER);
        }
    }

    private void joinResponseBody(StringBuilder responseBuilder) {
        responseBuilder.append(LINE_DELIMITER)
                .append(responseBody);
    }
}
