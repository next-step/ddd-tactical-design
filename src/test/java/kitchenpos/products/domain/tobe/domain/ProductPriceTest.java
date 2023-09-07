package kitchenpos.products.domain.tobe.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

    @Test
    void nullPrice() {
        assertThatThrownBy(() -> ProductPrice.of(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void minusPrice() {
        assertThatThrownBy(() -> ProductPrice.of(-1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void changePrice() {
        var price = ProductPrice.of(30_000L);
        var newPrice = price.changePrice(30_001L);
        assertThat(newPrice.isSamePrice(ProductPrice.of(30_001L))).isTrue();
    }

    @Test
    void isGreaterThan1() {
        var price = ProductPrice.of(30_000L);
        assertThat(price.isGreaterThan(ProductPrice.of(29_999L))).isTrue();
    }

    @ParameterizedTest
    @ValueSource(longs = {30000, 30001})
    void isGreaterThan2(long value) {
        var price = ProductPrice.of(30_000L);
        assertThat(price.isGreaterThan(ProductPrice.of(value))).isFalse();
    }

    @Test
    void addPrice() {
        var price1 = ProductPrice.of(30_000L);
        var price2 = ProductPrice.of(10_000L);
        assertThat(price1.add(price2)).isEqualTo(ProductPrice.of(40_000L));
    }

    @Test
    void isSamePrice() {
        var price1 = ProductPrice.of(30_000L);
        var price2 = ProductPrice.of(30_000L);
        assertThat(price1.isSamePrice(price2)).isTrue();
    }

    @Test
    void isSameObject() {
        var price1 = ProductPrice.of(30_000L);
        var price2 = ProductPrice.of(30_000L);
        assertThat(price1).isEqualTo(price2);
    }
}