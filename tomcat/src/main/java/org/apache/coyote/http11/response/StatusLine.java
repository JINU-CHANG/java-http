package org.apache.coyote.http11.response;

public class StatusLine {

    private String httpVersion;
    private int statusCode;
    private String statusMessage;

    public StatusLine() {
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode.getStatusCode();
        this.statusMessage = statusCode.getStatusMessage();
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
