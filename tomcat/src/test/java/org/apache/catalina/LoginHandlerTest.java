package org.apache.catalina;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.apache.coyote.http11.HttpHeaderName.SET_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;

class LoginHandlerTest {

    private final Handler handler = new LoginHandler();

    @DisplayName("로그인 성공시 index.html 리다이렉트")
    @Test
    void login_success_redirect() throws IOException {
        // given
        String loginSuccessURI = "GET http://localhost:8080/login?account=gugu&password=password HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(loginSuccessURI.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);
        HttpResponse httpResponse = handler.handle(httpRequest);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(302);
        assertThat(httpResponse.getHeader("Location")).isEqualTo("/index.html");
    }

    @DisplayName("로그인 실패시 401.html 리다이렉트")
    @Test
    void login_fail_redirect() throws IOException {
        String loginSuccessURI = "GET http://localhost:8080/login?account=zeze&password=password HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(loginSuccessURI.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);
        HttpResponse httpResponse = handler.handle(httpRequest);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(302);
        assertThat(httpResponse.getHeader("Location")).isEqualTo("/401.html");
    }

    @DisplayName("로그인 성공시 쿠키 반환")
    @Test
    void login_success_cookie() throws IOException {
        String loginSuccessURI = "GET http://localhost:8080/login?account=zeze&password=password HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(loginSuccessURI.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);
        HttpResponse httpResponse = handler.handle(httpRequest);

        // then
        assertThat(httpResponse.getHeader("Set-Cookie")).isNotEmpty();
    }

    @DisplayName("로그인 성공시 세션 저장")
    @Test
    void login_success_session() throws IOException {
        // given
        String loginSuccessURI = "GET http://localhost:8080/login?account=gugu&password=password HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(loginSuccessURI.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);
        HttpResponse httpResponse = handler.handle(httpRequest);

        // then
        String setCookieValue = httpResponse.getHeader(SET_COOKIE.name);
        String cookieKeyValue = setCookieValue.split("; ")[0];
        String cookieValue = cookieKeyValue.split("=")[1];
        Assertions.assertThat(SessionManager.findSession(cookieValue).getId()).isEqualTo(cookieValue);
    }
}
