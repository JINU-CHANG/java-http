package org.apache.coyote.http11.response;

import org.apache.coyote.http11.common.HttpVersion;

public class StatusLine {

    private HttpVersion httpVersion;
    private int statusCode;
    private String statusMessage;

    public StatusLine() {
    }

    public void setHttpVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode.getStatusCode();
        this.statusMessage = statusCode.getStatusMessage();
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
