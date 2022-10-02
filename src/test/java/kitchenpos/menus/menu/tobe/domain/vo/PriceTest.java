package kitchenpos.menus.menu.tobe.domain.vo;

import kitchenpos.menus.menu.tobe.domain.vo.exception.InvalidPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @DisplayName("가격을 생성한다.")
    @Test
    void valueOf() {
        final Price price = Price.valueOf(10_000L);

        assertAll(
                () -> assertThat(price).isEqualTo(Price.valueOf(10_000L)),
                () -> assertThat(price.value()).isEqualTo(BigDecimal.valueOf(10_000L))
        );
    }

    @ParameterizedTest(name = "가격은 0원 이상이여야 한다. price={0}")
    @NullSource
    @ValueSource(longs = {-10_000L, -20_000L})
    void invalid_price(final Long price) {
        assertThatThrownBy(() -> Price.valueOf(price))
                .isInstanceOf(InvalidPriceException.class);
    }

    @DisplayName("수량을 곱하면 현재 가격 X 수량의 값이 나온다.")
    @Test
    void multiply() {
        final Price price = Price.valueOf(10_000L);

        assertThat(price.multiply(Quantity.valueOf(3L))).isEqualTo(Price.valueOf(30_000L));
    }

    @DisplayName("가격을 비교한다.")
    @Nested
    class CompareToTest {

        @DisplayName("큰 가격과 비교하면 음수을 리턴한다.")
        @Test
        void compareToBigger() {
            final Price price = Price.valueOf(10_000L);
            final Price biggerPrice = Price.valueOf(11_000L);

            assertThat(price.compareTo(biggerPrice)).isLessThan(0);
        }

        @DisplayName("작은 가격과 비교하면 양수을 리턴한다.")
        @Test
        void compareToLess() {
            final Price price = Price.valueOf(10_000L);
            final Price biggerPrice = Price.valueOf(9_000L);

            assertThat(price.compareTo(biggerPrice)).isGreaterThan(0);
        }

        @DisplayName("같은 가격과 비교하면 0을 리턴한다.")
        @Test
        void compareToSame() {
            final Price price = Price.valueOf(10_000L);
            final Price biggerPrice = Price.valueOf(10_000L);

            assertThat(price.compareTo(biggerPrice)).isZero();
        }
    }
}
