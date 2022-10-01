package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @Test
    @DisplayName("가격 생성이 가능하다")
    void constrcutor() {
        final Price price = new Price(BigDecimal.ONE);
        assertThat(price).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("가격은 필수이다")
    @NullSource
    void create_price_with_null(final BigDecimal value) {
        assertThatThrownBy(() -> new Price(value))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("가격은 0보다 커야한다")
    @ValueSource(longs = { -1})
    void create_price_with_zero_and_null(final Long value) {
        assertThatThrownBy(() -> new Price(BigDecimal.valueOf(value)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
