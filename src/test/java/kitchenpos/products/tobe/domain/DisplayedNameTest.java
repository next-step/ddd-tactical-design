package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {

    @DisplayName("`DisplayedName` 생성 시 `name`이 존재하지 않으면 IllegalArgumentException을 던진다")
    @NullSource
    @ParameterizedTest
    void DisplayedName(final String name) {
        assertThatThrownBy(() -> new DisplayedName(name)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름은 필수입니다");
    }

    @DisplayName("`DisplayedName`은 `name`을 반환한다.")
    @ValueSource(strings = "후라이드")
    @ParameterizedTest
    void value(final String name) {
        final DisplayedName actual = new DisplayedName(name);
        assertThat(actual.value()).isEqualTo(name);
    }
}
