package kitchenpos.global.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class StringUtilsTest {

    @DisplayName("빈 문자열 검증")
    @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
    @NullAndEmptySource
    void isBlank(String actual) {
        assertThat(StringUtils.isBlank(actual))
                .isTrue();
    }
}
