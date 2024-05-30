package kitchenpos.products.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {
    @DisplayName("이름을 등록한다.")
    @Test
    void create() {
        String expected = "후라이드";
        DisplayedName actual = DisplayedName.createDisplayedName(expected, new FakeProfanities());

        assertThat(actual.getName()).isEqualTo(expected);
    }

    @DisplayName("이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설이 포함된 이름"})
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> DisplayedName.createDisplayedName(name, new FakeProfanities()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
