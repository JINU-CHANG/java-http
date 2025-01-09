package org.apache.coyote.http11.response;

public enum StatusCode {

    OK(200, "OK"),
    FOUND(302, "FOUND"),
    ;

    private final int statusCode;
    private final String statusMessage;

    StatusCode(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
