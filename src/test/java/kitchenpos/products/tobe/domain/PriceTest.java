package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class PriceTest {

    @DisplayName("상품의 가격을 생성할 수 있다.")
    @ValueSource(strings = "8000")
    @ParameterizedTest
    void create_Price(final BigDecimal price) {
        final Price actual = new Price(price);

        assertThat(actual.getValue()).isEqualTo(price);
    }

    @DisplayName("상품의 가격은 null이 될 수 없다.")
    @NullSource
    @ParameterizedTest
    void create_null_Price(final BigDecimal price) {
        assertThatThrownBy(() -> new Price(price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격은 0원 이상이어야 한다.")
    @ValueSource(strings = "-8000")
    @ParameterizedTest
    void create_negative_Price(final BigDecimal price) {
        assertThatThrownBy(() -> new Price(price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 가격 간 동등성을 확인할 수 있다.")
    @Test
    void equals() {
        final Price price1 = new Price(BigDecimal.valueOf(1000L));
        final Price price2 = new Price(BigDecimal.valueOf(1000L));
        assertThat(price1).isEqualTo(price2);
    }
}
