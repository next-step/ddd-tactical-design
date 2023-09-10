package kitchenpos.products.domain.tobe.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

    @DisplayName("상품 가격은 null을 가질 수 없다.")
    @Test
    void nullPrice() {
        assertThatThrownBy(() -> ProductPrice.of(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품가격은 0원 이상이어야 한다")
    @Test
    void minusPrice() {
        assertThatThrownBy(() -> ProductPrice.of(-1))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품가격을 변경하면, 변경된 가격을 확인 할 수 있다.")
    @Test
    void changePrice() {
        var price = ProductPrice.of(30_000L);
        var newPrice = price.changePrice(30_001L);
        assertThat(newPrice.isSamePrice(ProductPrice.of(30_001L))).isTrue();
    }

    @DisplayName("상품가격이 입력한 금액보다 크다")
    @Test
    void isGreaterThan1() {
        var price = ProductPrice.of(30_000L);
        assertThat(price.isGreaterThan(ProductPrice.of(29_999L))).isTrue();
    }

    @DisplayName("상품가격이 입력한 가격과 같거나 크다.")
    @ParameterizedTest
    @ValueSource(longs = {30000, 30001})
    void isGreaterThan2(long value) {
        var price = ProductPrice.of(30_000L);
        assertThat(price.isGreaterThan(ProductPrice.of(value))).isFalse();
    }

    @DisplayName("상품가격을 더하면 합계 상품 금액을 확인 할 수 있다.")
    @Test
    void addPrice() {
        var price1 = ProductPrice.of(30_000L);
        var price2 = ProductPrice.of(10_000L);
        assertThat(price1.add(price2)).isEqualTo(ProductPrice.of(40_000L));
    }

    @DisplayName("상품가격이 동일한 가격을 가지고 있다.")
    @Test
    void isSamePrice() {
        var price1 = ProductPrice.of(30_000L);
        var price2 = ProductPrice.of(30_000L);
        assertThat(price1.isSamePrice(price2)).isTrue();
    }

    @DisplayName("동일한 상품가격은 같은 객체로 간주한다.")
    @Test
    void isSameObject() {
        var price1 = ProductPrice.of(30_000L);
        var price2 = ProductPrice.of(30_000L);
        assertThat(price1).isEqualTo(price2);
    }
}
