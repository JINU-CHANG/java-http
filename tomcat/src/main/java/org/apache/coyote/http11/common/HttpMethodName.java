package org.apache.coyote.http11.common;

import java.util.Arrays;

public enum HttpMethodName {

    GET("GET"),
    POST("POST"),
    ;

    public final String name;

    HttpMethodName(String name) {
        this.name = name;
    }

    public static HttpMethodName from(String name) {
        return Arrays.stream(values())
                .filter(httpMethodName -> httpMethodName.name.equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 메서드입니다."));
    }
}
