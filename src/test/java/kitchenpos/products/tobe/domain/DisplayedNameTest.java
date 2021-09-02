package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class DisplayedNameTest {

    @DisplayName("`DisplayedName`은 `name`을 `value`한다.")
    @ValueSource(strings = "후라이드")
    @ParameterizedTest
    void value(final String name) {
        final DisplayedName actual = new DisplayedName(name);
        assertThat(actual.value()).isEqualTo(name);
    }
}
