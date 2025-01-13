package org.apache.catalina;

import com.techcourse.controller.RegisterController;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.apache.coyote.http11.common.HttpHeaderName.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;

class RegisterControllerTest {

    @DisplayName("회원가입 완료후 index.html로 리다이렉트 성공")
    @Test
    void registerSuccessAndRedirectToIndexPage() throws Exception {
        // given
        String mockHttpRequest = "POST /register HTTP/1.1\n"
                + "Host: localhost:8080\n"
                + "Connection: keep-alive\n"
                + "Content-Length: 80\n"
                + "Content-Type: application/x-www-form-urlencoded\n"
                + "Accept: */*\n"
                + "\n"
                + "account=gugu&password=password&email=hkkang%40woowahan.com";
        InputStream inputStream = new ByteArrayInputStream(mockHttpRequest.getBytes());
        HttpRequest httpRequest = new HttpRequest(inputStream);
        RegisterController registerServlet = new RegisterController();

        // when
        HttpResponse httpResponse = new HttpResponse();
        registerServlet.service(httpRequest, httpResponse);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(302);
        assertThat(httpResponse.getHeader(LOCATION)).isEqualTo("/index.html");
    }
}
