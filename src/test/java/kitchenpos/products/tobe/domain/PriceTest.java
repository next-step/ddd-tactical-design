package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @ParameterizedTest
    @MethodSource("priceMethodSource")
    @DisplayName("0이상의 가격을 입력할 수 있다.")
    void price(Long price) {
        Price price1 = new Price(price);
        Price price2 = new Price(price);

        assertThat(price1).isEqualTo(price2);
    }

    @Test
    @DisplayName("음수의 가격을 입력하면 오류가 발생한다.")
    void price_positive() {
        assertThatThrownBy(() -> new Price(-1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public static Stream<Long> priceMethodSource() {
        return Stream.of(100L, 0L, 200L);
    }
}