package kitchenpos.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " ", "  ", "           "})
    @DisplayName("isBlank의 매개변수가 null이나 빈 문자열이면 true 반환")
    void nullAndEmptyTest(String source) {
        assertTrue(StringUtils.isBlank(source));
    }

    @ParameterizedTest
    @ValueSource(strings = {"뭐해 라는 두글자에", "네가 보고 싶어 나의 속마음을 담아 우", "이모티콘 하나하나 속에", "달라지는 내 미묘한 심리를 알까 우"})
    @DisplayName("isBlank의 매개변수가 null이나 빈 문자열이 아니면 false 반환")
    void isBlankTest(String source) {
        assertFalse(StringUtils.isBlank(source));
    }
}
