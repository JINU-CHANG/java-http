package org.apache.coyote.http11.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContentTypeTest {

    @DisplayName("ContentType 생성 성공")
    @Test
    void create() {
        // given
        String type = "text/html";
        String parameter = "charset";
        String value = "UTF-8";

        String stringContentType = type + "; " + parameter + "=" + value;

        // when
        ContentType contentType = new ContentType(stringContentType);

        // then
        assertThat(contentType.getType()).isEqualTo(type);
        assertThat(contentType.getParameter(parameter)).isEqualTo(value);
    }

    @DisplayName("ContentType toString 반환 성공")
    @Test
    void contentType_toString() {
        // given
        String type = "text/html";
        String parameter = "charset";
        String value = "UTF-8";

        String stringContentType = type + "; " + parameter + "=" + value;

        // when
        ContentType contentType = new ContentType(stringContentType);

        // then
        assertThat(contentType.toString()).isEqualTo(stringContentType);
    }
}
