package org.apache.coyote.http11;

public enum HttpHeaderName {

    COOKIE("Cookie"),
    SET_COOKIE("Set-Cookie"),
    ;

    public final String name;

    HttpHeaderName(String name) {
        this.name = name;
    }
}
