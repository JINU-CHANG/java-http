package org.apache.catalina;

import org.apache.catalina.controller.FileController;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class FileControllerTest {

    @DisplayName("파일 확장자 존재시 파일 반환")
    @Test
    void getFile() throws Exception {
        // given
        FileController fileController = new FileController();
        String mockHttpRequest = "GET /index.html HTTP/1.1\n";
        InputStream inputStream = new ByteArrayInputStream(mockHttpRequest.getBytes());
        HttpRequest httpRequest = new HttpRequest(inputStream);

        // when & then
        HttpResponse httpResponse = new HttpResponse();
        fileController.service(httpRequest, httpResponse);

        Assertions.assertThat(httpResponse.getResponseBody()).isNotEmpty();
    }
}
