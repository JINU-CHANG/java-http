package org.apache.coyote.http11.response;

public enum StatusCode {

    OK(200),
    FOUND(302),
    ;

    public final int statusCode;

    StatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
