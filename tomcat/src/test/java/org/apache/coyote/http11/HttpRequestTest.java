package org.apache.coyote.http11;

import org.apache.coyote.http11.request.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @DisplayName("RequestLine 저장 성공")
    @Test
    void saveRequestLine() throws IOException {
        // given
        String mockHttpRequest = "GET / HTTP/1.1\r\nHost: localhost:8080\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(mockHttpRequest.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);

        // then
        assertThat(httpRequest.getMethod()).isEqualTo("GET");
        assertThat(httpRequest.getURI()).isEqualTo("/");
    }

    @DisplayName("QueryString 저장 성공")
    @Test
    void saveQueryString() throws IOException {
        // given

        String mockHttpRequest = "GET http://localhost:8080/login?account=gugu&password=password HTTP/1.1\r\n";
        InputStream inputStream = new ByteArrayInputStream(mockHttpRequest.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);

        // then
        assertThat(httpRequest.getParameter("account")).isEqualTo("gugu");
        assertThat(httpRequest.getParameter("password")).isEqualTo("password");
    }

    @DisplayName("RequestHeader 저장 성공")
    @Test
    void saveHeader() throws IOException {
        String mockHttpRequest = "GET / HTTP/1.1\r\nHost: localhost:8080\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(mockHttpRequest.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);

        // then
        assertThat(httpRequest.getHeader("Host")).isEqualTo("localhost:8080");
    }

    @DisplayName("RequestBody 저장 성공")
    @Test
    void saveRequestBody() throws IOException {
        // given
        String mockRequestBody = "account=gugu&password=password&email=hkkang%40woowahan.com";
        String mockHttpRequest = "POST /register HTTP/1.1\r\n"
                + "Host: localhost:8080\r\n"
                + "Connection: keep-alive\r\n"
                + "Content-Length: 80\r\n"
                + "Content-Type: application/x-www-form-urlencoded\r\n"
                + "Accept: */*\r\n"
                + "\r\n"
                + mockRequestBody;
        InputStream inputStream = new ByteArrayInputStream(mockHttpRequest.getBytes());

        // when
        HttpRequest httpRequest = new HttpRequest(inputStream);

        // then
        assertThat(httpRequest.getRequestBody()).isEqualTo(mockRequestBody);
    }
}
