package org.apache.coyote.http11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpCookieTest {

    @DisplayName("쿠키 저장 성공")
    @Test
    void createCookie() {
        // given & when
        String JSESSIONID_KEY = "JSESSIONID";
        String JSESSIONID_VALUE = "656cef62-e3c4-40bc-a8df-94732920ed46";

        HttpCookie cookie = new HttpCookie(
                "yummy_cookie=choco; tasty_cookie=strawberry; " + JSESSIONID_KEY + "=" + JSESSIONID_VALUE);

        // then
        Assertions.assertThat(cookie.getValue(JSESSIONID_KEY)).isEqualTo(JSESSIONID_VALUE);
    }
}
