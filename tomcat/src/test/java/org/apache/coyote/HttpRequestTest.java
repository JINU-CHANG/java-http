package org.apache.coyote;

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
}
