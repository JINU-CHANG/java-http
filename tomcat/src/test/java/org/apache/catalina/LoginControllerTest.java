package org.apache.catalina;

import com.techcourse.controller.LoginController;
import com.techcourse.controller.RegisterController;
import org.apache.catalina.controller.Controller;
import org.apache.catalina.session.SessionManager;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.apache.coyote.http11.common.HttpHeaderName.LOCATION;
import static org.apache.coyote.http11.common.HttpHeaderName.SET_COOKIE;
import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {

    private final Controller controller = new LoginController();

    @DisplayName("로그인 성공시 index.html 리다이렉트")
    @Test
    void login_success_redirect() throws Exception {
        // given
        String loginSuccessURI = "POST http://localhost:8080/login?account=gugu&password=password HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(loginSuccessURI.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);
        HttpResponse httpResponse = new HttpResponse();
        controller.service(httpRequest, httpResponse);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(302);
        assertThat(httpResponse.getHeader(LOCATION)).isEqualTo("/index.html");
    }

    @DisplayName("로그인 실패시 401.html 리다이렉트")
    @Test
    void login_fail_redirect() throws Exception {
        String loginSuccessURI = "POST http://localhost:8080/login?account=zezeze&password=password HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(loginSuccessURI.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);
        HttpResponse httpResponse = new HttpResponse();
        controller.service(httpRequest, httpResponse);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(302);
        assertThat(httpResponse.getHeader(LOCATION)).isEqualTo("/401.html");
    }

    @DisplayName("로그인 성공시 쿠키 반환")
    @Test
    void login_success_cookie() throws Exception {
        String loginSuccessURI = "POST http://localhost:8080/login?account=gugu&password=password HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(loginSuccessURI.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);
        HttpResponse httpResponse = new HttpResponse();
        controller.service(httpRequest, httpResponse);

        // then
        assertThat(httpResponse.getHeader(SET_COOKIE)).isNotEmpty();
    }

    @DisplayName("로그인 성공시 세션 저장")
    @Test
    void login_success_session() throws Exception {
        // given
        String loginSuccessURI = "POST http://localhost:8080/login?account=gugu&password=password HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(loginSuccessURI.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);
        HttpResponse httpResponse = new HttpResponse();
        controller.service(httpRequest, httpResponse);

        // then
        assertThat(SessionManager.findSession(parseJSESSIONCookieValue(httpResponse)).getId()).isNotEmpty();
    }

    @DisplayName("쿠키값이 존재할 때 리다이렉트 성공")
    @Test
    void login_success_cookie_exist() throws Exception {
        // given
        String registerSuccessURI = "POST /register HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: 80\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n"
                + "\n"
                + "account=zeze&password=password&email=hkkang%40woowahan.com";
        InputStream registerInputStream = new ByteArrayInputStream(registerSuccessURI.getBytes());
        RegisterController registerServlet = new RegisterController();
        HttpResponse httpResponse = new HttpResponse();
        registerServlet.service(new HttpRequest(registerInputStream), httpResponse);

        String loginSuccessURI = "POST http://localhost:8080/login?account=zeze&password=password HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n";
        InputStream loginInputStream = new ByteArrayInputStream(loginSuccessURI.getBytes());
        HttpResponse loginResponse = new HttpResponse();
        controller.service(new HttpRequest(loginInputStream), loginResponse);
        String jsessionid = parseJSESSIONCookieValue(loginResponse);

        String loginSuccessURI2 = "POST http://localhost:8080/login?account=zeze&password=password HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Accept: */*\n"
                + "Cookie: yummy_cookie=choco; tasty_cookie=strawberry; JSESSIONID=" + jsessionid;
        InputStream loginInputStream2 = new ByteArrayInputStream(loginSuccessURI2.getBytes());

        // when
        HttpRequest httpRequest2 = new HttpRequest(loginInputStream2);
        HttpResponse httpResponse2 = new HttpResponse();
        controller.service(httpRequest2, httpResponse2);

        // then
        assertThat(parseJSESSIONCookieValue(httpResponse2)).isEqualTo(jsessionid);
    }

    private String parseJSESSIONCookieValue(HttpResponse httpResponse) {
        String setCookieValue = httpResponse.getHeader(SET_COOKIE);
        String cookieKeyValue = setCookieValue.split("; ")[0];
        return cookieKeyValue.split("=")[1];
    }

    @DisplayName("/login 요청시 login.html 파일 반환")
    @Test
    void getLoginPage() throws Exception {
        // given
        LoginController loginController = new LoginController();
        String mockHttpRequest = "GET /login HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(mockHttpRequest.getBytes());
        HttpRequest httpRequest = new HttpRequest(inputStream);

        // when & then
        HttpResponse httpResponse = new HttpResponse();
        loginController.service(httpRequest, httpResponse);

        Assertions.assertThat(httpResponse.getResponseBody()).isNotEmpty();
    }
}
