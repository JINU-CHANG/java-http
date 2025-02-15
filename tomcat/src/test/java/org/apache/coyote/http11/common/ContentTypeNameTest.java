package org.apache.coyote.http11.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.apache.coyote.http11.common.ContentTypeName.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTypeNameTest {

    @DisplayName("ContentType 반환 성공")
    @Test
    void from() {
        // given
        String textHtmlName = TEXT_HTML.getName();

        // when
        ContentTypeName contentTypeName = ContentTypeName.from(textHtmlName);

        // then
        Assertions.assertThat(contentTypeName).isEqualTo(TEXT_HTML);
    }

    @DisplayName("ContentType 반환 실패 : 타입이 존재하지 않을 때")
    @Test
    void from_failed() {
        // given
        String textHtmlName = "fail";

        // when
        assertThatThrownBy(() -> ContentTypeName.from(textHtmlName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바르지 않은 컨텐트 타입입니다.");
    }
}
