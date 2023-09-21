package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PriceTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void 가격은_0원_이상이다(int price) {
        assertDoesNotThrow(() -> new Price(price));
    }

    @Test
    void 가격은_음수일_수_없다() {
        assertThatThrownBy(() -> new Price(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0원 이상이어야 합니다. 현재 값: -1");
    }

    @Test
    void 가격을_서로_비교할_수_있다() {
        Price one = new Price(1);
        Price ten = new Price(10);

        assertThat(one.isSmallerOrEqualTo(ten)).isTrue();
        assertThat(ten.isSmallerOrEqualTo(one)).isFalse();
    }

    @Test
    void 가격에_수량을_곱한_가격을_구할_수_있다() {
        Price price = new Price(1000);
        Quantity quantity = new Quantity(2);

        assertThat(price.multiplyQuantity(quantity)).isEqualTo(new Price(2000));
    }

    @Test
    void 가격을_서로_더할_수_있다() {
        Price price = new Price(1000);

        assertThat(price.sum(price)).isEqualTo(new Price(2000));
    }

    @Test
    void Price_동등성_비교() {
        Price actual = new Price(0);

        assertThat(actual.equals(actual)).isTrue();

        assertThat(actual.equals(null)).isFalse();
        assertThat(actual.equals("wrong class")).isFalse();

        assertThat(actual.equals(new Price(0))).isTrue();
        assertThat(actual.equals(new Price(1))).isFalse();
    }

    @Test
    void Price_hashCode() {
        Price actual = new Price(0);

        assertThat(actual.hashCode()).isEqualTo(new Price(0).hashCode());
        assertThat(actual.hashCode()).isNotEqualTo(new Price(1).hashCode());
    }
}
