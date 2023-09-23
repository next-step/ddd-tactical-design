package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {
    @ParameterizedTest
    @ValueSource(longs = {0, 1})
    void 가격은_0원_이상이다(Long price) {
        assertDoesNotThrow(() -> new Price(price));
    }

    @Test
    void 가격은_음수일_수_없다() {
        assertThatThrownBy(() -> new Price(-1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0원 이상이어야 합니다. 현재 값: -1");
    }

    @Test
    void 가격을_서로_비교할_수_있다() {
        Price one = new Price(1L);
        Price ten = new Price(10L);

        assertThat(one.isSmallerOrEqualTo(ten)).isTrue();
        assertThat(ten.isSmallerOrEqualTo(one)).isFalse();
    }

    @Test
    void 가격에_수량을_곱한_가격을_구할_수_있다() {
        Price price = new Price(1000L);
        Quantity quantity = new Quantity(2);

        assertThat(price.multiplyQuantity(quantity)).isEqualTo(new Price(2000L));
    }

    @Test
    void 가격을_서로_더할_수_있다() {
        Price price = new Price(1000L);

        assertThat(price.sum(price)).isEqualTo(new Price(2000L));
    }

    @Test
    void Price_동등성_비교() {
        Price actual = new Price(0L);

        assertThat(actual.equals(actual)).isTrue();

        assertThat(actual.equals(null)).isFalse();
        assertThat(actual.equals("wrong class")).isFalse();

        assertThat(actual.equals(new Price(0L))).isTrue();
        assertThat(actual.equals(new Price(1L))).isFalse();
    }

    @Test
    void Price_hashCode() {
        Price actual = new Price(0L);

        assertThat(actual.hashCode()).isEqualTo(new Price(0L).hashCode());
        assertThat(actual.hashCode()).isNotEqualTo(new Price(1L).hashCode());
    }
}
