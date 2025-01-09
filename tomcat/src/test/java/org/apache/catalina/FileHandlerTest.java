package org.apache.catalina;

import org.apache.coyote.http11.request.HttpRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

class FileHandlerTest {

    @DisplayName("파일 형태가 아닌 요청(/register) 핸들 성공")
    @Test
    void getRegisterPage() throws IOException {
        // given
        FileHandler fileHandler = new FileHandler();
        String mockHttpRequest = "GET /register HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(mockHttpRequest.getBytes());
        HttpRequest httpRequest = new HttpRequest(inputStream);

        // when & then
        Assertions.assertThat(fileHandler.canHandle(httpRequest)).isTrue();
    }
}
