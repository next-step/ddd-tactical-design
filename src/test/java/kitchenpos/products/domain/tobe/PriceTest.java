package kitchenpos.products.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @DisplayName("가격을 등록한다.")
    @Test
    void create() {
        BigDecimal expected = BigDecimal.valueOf(16000);
        Price actual = Price.createPrice(expected);

        assertThat(actual.getPriceValue()).isEqualTo(expected);
    }

    @DisplayName("가격이 0보다 작으면 가격을 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
        assertThatThrownBy(() -> Price.createPrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격을 변경한다.")
    @Test
    void change() {
        BigDecimal expected = BigDecimal.valueOf(10000);

        Price actual = Price.createPrice(BigDecimal.valueOf(16000));
        actual = actual.changePrice(Price.createPrice(expected));

        assertThat(actual.getPriceValue()).isEqualTo(expected);
    }

    @DisplayName("가격이 0보다 작으면 가격을 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void change(final BigDecimal price) {
        Price orginPrice = Price.createPrice(BigDecimal.valueOf(16000));

        assertThatThrownBy(() -> orginPrice.changePrice(Price.createPrice(price)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
