package org.apache.coyote.http11.response;

public enum StatusCode {

    OK(200, "OK"),
    FOUND(302, "FOUND"),
    ;

    public final int statusCode;
    public final String statusMessage;

    StatusCode(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
