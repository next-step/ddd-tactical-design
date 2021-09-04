package kitchenpos.common.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {

    @DisplayName("이름 생성 시 이름 값이 존재하지 않거나 비어 있으면 IllegalArgumentException을 던진다")
    @NullAndEmptySource
    @ParameterizedTest
    void DisplayedName(final String name) {
        assertThatThrownBy(() -> new DisplayedName(name)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수고, 비워둘 수 없습니다");
    }

    @DisplayName("이름은 이름 값을 반환한다.")
    @ValueSource(strings = "후라이드")
    @ParameterizedTest
    void value(final String name) {
        final DisplayedName actual = new DisplayedName(name);
        assertThat(actual.value()).isEqualTo(name);
    }
}
