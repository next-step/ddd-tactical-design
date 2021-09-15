package kitchenpos.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class PriceTest {

    @DisplayName("가격을 생성할 수 있다.")
    @ValueSource(strings = {"0", "18000"})
    @ParameterizedTest
    void 생성(final BigDecimal price) {
        assertDoesNotThrow(
            () -> new Price(price)
        );
    }

    @DisplayName("가격은 null이 될 수 없다.")
    @NullSource
    @ParameterizedTest
    void null가격(final BigDecimal price) {
        assertThatThrownBy(() -> new Price(price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격은 0원 이상이어야 한다.")
    @ValueSource(strings = "-8000")
    @ParameterizedTest
    void 마이너스가격(final BigDecimal price) {
        assertThatThrownBy(() -> new Price(price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격 간 덧셈이 가능하다.")
    @Test
    void 가격덧셈() {
        final Price price1 = new Price(BigDecimal.valueOf(1_000L));
        final Price price2 = new Price(BigDecimal.valueOf(2_000L));

        final Price expected = new Price(BigDecimal.valueOf(3_000L));
        assertThat(price1.add(price2)).isEqualTo(expected);
    }

    @DisplayName("가격 간 덧셈이 가능하다.")
    @Test
    void 가격덧셈2() {
        final Price price1 = new Price(BigDecimal.valueOf(1_000L));
        final Price price2 = new Price(BigDecimal.valueOf(0L));

        final Price expected = new Price(BigDecimal.valueOf(1_000L));
        assertThat(price1.add(price2)).isEqualTo(expected);
    }

    @DisplayName("가격과 수량 간 곱셈이 가능하다.")
    @Test
    void 가격곱셈() {
        final Price price = new Price(BigDecimal.valueOf(1_000L));
        final Quantity quantity = new Quantity(2);

        final Price expected = new Price(BigDecimal.valueOf(2_000L));
        assertThat(price.multiply(quantity)).isEqualTo(expected);
    }

    @DisplayName("가격과 수량 간 곱셈이 가능하다.")
    @Test
    void 가격곱셈2() {
        final Price price = new Price(BigDecimal.valueOf(1_000L));
        final Quantity quantity = new Quantity(0);

        final Price expected = Price.ZERO;
        assertThat(price.multiply(quantity)).isEqualTo(expected);
    }

    @DisplayName("가격 간 동등성을 확인할 수 있다.")
    @Test
    void equals() {
        final Price price1 = new Price(BigDecimal.valueOf(1000L));
        final Price price2 = new Price(BigDecimal.valueOf(1000L));

        assertThat(price1).isEqualTo(price2);
    }

    @DisplayName("두 가격을 비교할 수 있다.")
    @Test
    void 가격비교() {
        final Price price1 = new Price(BigDecimal.valueOf(1_000L));
        final Price price2 = new Price(BigDecimal.valueOf(2_000L));

        assertThat(price1.compareTo(price2)).isEqualTo(-1);
    }

    @DisplayName("두 가격을 비교할 수 있다.")
    @Test
    void 가격비교2() {
        final Price price1 = new Price(BigDecimal.valueOf(1_000L));
        final Price price2 = new Price(BigDecimal.valueOf(1_000L));

        assertThat(price1.compareTo(price2)).isEqualTo(0);
    }

    @DisplayName("두 가격을 비교할 수 있다.")
    @Test
    void 가격비교3() {
        final Price price1 = new Price(BigDecimal.valueOf(2_000L));
        final Price price2 = new Price(BigDecimal.valueOf(1_000L));

        assertThat(price1.compareTo(price2)).isEqualTo(1);
    }
}
