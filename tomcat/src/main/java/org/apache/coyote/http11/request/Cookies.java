package org.apache.coyote.http11.request;

import org.apache.coyote.http11.common.Cookie;
import java.util.HashMap;
import java.util.Map;

public class Cookies {

    private static final String COOKIE_DELIMITER = "; ";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";

    private final Map<String, Cookie> cookies;

    public Cookies(String line) {
        this.cookies = new HashMap<>();

        String[] cookies = line.split(COOKIE_DELIMITER);
        for (String cookie : cookies) {
            String[] keyValue = cookie.split(COOKIE_KEY_VALUE_DELIMITER);
            this.cookies.put(keyValue[0], new Cookie(keyValue[0], keyValue[1]));
        }
    }

    public String getValue(String key) {
        if (!cookies.containsKey(key)) return null;
        return cookies.get(key).getValue();
    }
}
