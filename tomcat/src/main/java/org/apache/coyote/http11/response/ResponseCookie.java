package org.apache.coyote.http11.response;

import org.apache.coyote.http11.Cookie;
import java.time.Duration;

public class ResponseCookie extends Cookie {

    private final Duration maxAge;
    private final String domain;
    private final String path;
    private final boolean secure;
    private final boolean httpOnly;
    private final String sameSite;

    public ResponseCookie(String key, String value,
                          Duration maxAge, String domain,
                          String path, boolean secure,
                          boolean httpOnly, String sameSite) { // TODO 리팩토링
        super(key, value);
        this.maxAge = maxAge;
        this.domain = domain;
        this.path = path;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.sameSite = sameSite;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getKey()).append('=').append(this.getValue());
        sb.append("; Path=").append(path);
        sb.append("; Domain=").append(this.domain);
        sb.append("; Max-Age=").append(this.maxAge.getSeconds());
        sb.append("; Expires=").append(System.currentTimeMillis() + this.maxAge.toMillis());

        if (this.secure) {
            sb.append("; Secure");
        }

        if (this.httpOnly) {
            sb.append("; HttpOnly");
        }

        sb.append("; SameSite=").append(this.sameSite);
        return sb.toString();
    }
}
