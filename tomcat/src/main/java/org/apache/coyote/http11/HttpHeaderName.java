package org.apache.coyote.http11;

public enum HttpHeaderName {

    COOKIE("Cookie"),
    SET_COOKIE("Set-Cookie"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    ;

    public final String name;

    HttpHeaderName(String name) {
        this.name = name;
    }
}
