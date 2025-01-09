package org.apache.catalina;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterHandlerTest {

    @DisplayName("회원가입 완료후 index.html로 리다이렉트 성공")
    @Test
    void registerSuccessAndRedirectToIndexPage() throws IOException {
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
        RegisterHandler registerHandler = new RegisterHandler();

        // when
        HttpResponse httpResponse = registerHandler.handle(httpRequest);

        // then
        assertThat(httpResponse.getStatusCode()).isEqualTo(302);
        assertThat(httpResponse.getHeader("Location")).isEqualTo("index.html");
    }
}
