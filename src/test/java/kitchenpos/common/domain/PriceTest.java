package kitchenpos.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {
    @DisplayName("상품의 가격을 설정한다.")
    @Test
    void create() {
        final BigDecimal price = BigDecimal.valueOf(16000L);
        final Price actual = new Price(price);

        assertThat(actual.getValue()).isEqualTo(price);
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
