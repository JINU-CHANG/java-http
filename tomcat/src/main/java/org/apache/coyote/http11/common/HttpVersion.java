package org.apache.coyote.http11.common;

import java.util.Arrays;

public enum HttpVersion {

    HTTP_VERSION11("HTTP/1.1");

    public final String value;

    HttpVersion(String value) {
        this.value = value;
    }

    public static HttpVersion from (String value) {
        return Arrays.stream(values())
                .filter(httpVersion -> httpVersion.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 HTTP 버전입니다."));
    }
}
