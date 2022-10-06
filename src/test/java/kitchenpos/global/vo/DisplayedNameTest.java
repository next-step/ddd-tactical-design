package kitchenpos.global.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("이름")
class DisplayedNameTest {

    @DisplayName("이름 생성")
    @Test
    void createDisplayedName() {
        DisplayedName name = new DisplayedName("후라이드");

        assertThat(name.getValue()).isEqualTo("후라이드");
    }

    @DisplayName("이름이 null 또는 빈값이면 에러")
    @NullAndEmptySource
    @ParameterizedTest
    void createDisplayedNameNull(String value) {
        assertThatThrownBy(() -> new DisplayedName(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
