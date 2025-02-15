package org.apache.coyote.http11.common;

import java.util.Arrays;

public enum ContentTypeName {

    TEXT_HTML("text/html");

    private final String name;

    ContentTypeName(String name) {
        this.name = name;
    }

    public static ContentTypeName from(String name) {
        return Arrays.stream(values())
                .filter(contentTypeName -> contentTypeName.name.equals(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("올바르지 않은 컨텐트 타입입니다."));
    }

    public String getName() {
        return name;
    }
}
