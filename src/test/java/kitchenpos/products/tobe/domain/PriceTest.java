package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @DisplayName("`Price` 생성 시 `price`가 0 미만이면 IllegalArgumentException을 던진다.")
    @NullSource
    @ValueSource(strings = "-1000")
    @ParameterizedTest
    void Price(final BigDecimal price) {
        assertThatThrownBy(() -> new Price(price)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 필수고, 0 이상이어야 합니다");
    }

    @DisplayName("`Price`는 `price`를 반환한다.")
    @ValueSource(strings = {"0", "1000"})
    @ParameterizedTest
    void value(final BigDecimal price) {
        final Price actual = new Price(price);
        assertThat(actual.value()).isEqualTo(price);
    }
}
