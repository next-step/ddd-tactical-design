package kitchenpos.common.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @DisplayName("정상적으로 가격을 생성해보자")
    @ParameterizedTest
    @ValueSource(ints = {0, 1000, 5000, 10000})
    void createPrice(int inputPrice) {
        Price price = new Price(BigDecimal.valueOf(inputPrice));

        assertAll(
                () -> assertThat(price).isNotNull(),
                () -> assertThat(price.getPrice()).isEqualTo(BigDecimal.valueOf(inputPrice))
        );
    }

    @DisplayName("가격은 null이 될수 없다.")
    @ParameterizedTest
    @NullSource
    void invalidPrice(BigDecimal price) {
        assertThatThrownBy(
                () -> new Price(price)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격은 음수가 될수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-100, -5000, -10000})
    void negativePrice(int price) {
        assertThatThrownBy(
                () -> new Price(BigDecimal.valueOf(price))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
