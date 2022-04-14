package kitchenpos.global.domain.vo;

import kitchenpos.global.exception.IllegalPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {


    private static final BigDecimal NORMAL_PRICE_VALUE = BigDecimal.valueOf(1_000L);
    private static final Price NORMAL_PRICE = new Price(BigDecimal.valueOf(1_000L));
    private static final BigDecimal NEGATIVE_PRICE_VALUE = BigDecimal.valueOf(-1_000L);

    @Test
    @DisplayName("가격은 0보다 큰 값을 가져야 한다.")
    void create() {
        //when & then
        assertThatThrownBy(() -> new Price(NEGATIVE_PRICE_VALUE))
                .isInstanceOf(IllegalPriceException.class);
    }


    @Test
    @DisplayName("객체의 값이 동일한지 비교한다.")
    void isSame() {
        //given
        Price origin = new Price(NORMAL_PRICE_VALUE);
        assertThat(origin.isSame(NORMAL_PRICE)).isTrue();
    }

    @Test
    @DisplayName("가격 끼리 더할수 있다.")
    void add01() {
        Price price1 = new Price(NORMAL_PRICE_VALUE);
        Price price2 = new Price(NORMAL_PRICE_VALUE);

        assertThat(price1.add(price2)).isEqualTo(new Price(BigDecimal.valueOf(2000L)));
    }

    @Test
    @DisplayName("가격에 0을 더하면 동일한 객체를 돌려준다.")
    void add02() {
        Price price1 = new Price(NORMAL_PRICE_VALUE);
        assertThat(price1.add(new Price(BigDecimal.ZERO))).isEqualTo(price1);
    }

    @Test
    @DisplayName("가격에 수량을 곱할 수 있다.")
    void multiply01() {
        Price price1 = new Price(NORMAL_PRICE_VALUE);
        assertThat(price1.multiply(2L)).isEqualTo(new Price(BigDecimal.valueOf(2000L)));
    }

    @Test
    @DisplayName("가격에 수량 1을 곱하면 동일한 객체를 돌려준다.")
    void multiply02() {
        Price price1 = new Price(NORMAL_PRICE_VALUE);
        assertThat(price1.multiply(1L)).isEqualTo(price1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1000", "1500"})
    @DisplayName("가격이 더 작거나 같은지 확인한다.")
    void greaterThanEquals(BigDecimal other) {
        Price price = new Price(BigDecimal.valueOf(1000L));
        assertThat(price.lessThanEquals(new Price(other))).isTrue();
    }




}
