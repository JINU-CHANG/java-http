package org.apache.coyote.http11.common;

public enum Charset {

    UTF_8("utf-8");

    private final String name;

    Charset(String name) {
        this.name = name;
    }

    public static String getDefault() {
        return UTF_8.name;
    }
}
