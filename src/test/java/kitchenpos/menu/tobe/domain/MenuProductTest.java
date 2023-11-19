package kitchenpos.menu.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.Fixtures;
import kitchenpos.common.Price;
import kitchenpos.product.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성한다")
    @Test
    void testInitMenuProduct() {
        // given
        Price price = new Price(BigDecimal.valueOf(10_000L));
        int quantity = 1;

        // when // then
        assertDoesNotThrow(() -> new MenuProduct(UUID.randomUUID(), price, quantity));
    }

    @DisplayName("갯수가 0 미만이면 메뉴 상품을 생성할 수 없다")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    void testInitMenuProductIfQuantityIsNegative(int quantity) {
        // given
        Price price = new Price(BigDecimal.valueOf(10_000L));

        // when // then
        assertThatThrownBy(() -> new MenuProduct(UUID.randomUUID(), price, quantity))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 상품의 총 가격을 구한다")
    @Test
    void testCalculatePrice() {
        // given
        Product product = Fixtures.product();
        int quantity = 2;

        var menuProduct = Fixtures.menuProduct(product, quantity);

        // when
        var actual = menuProduct.calculatePrice();

        // then
        assertThat(actual.getValue()).isEqualTo(BigDecimal.valueOf(32_000L));
    }

    @DisplayName("메뉴 상품의 가격을 수정한다")
    @Test
    void testChangeMenuPrice() {
        // given
        var menuProduct = Fixtures.menuProduct();

        // when
        menuProduct.changePrice(Price.of(BigDecimal.ONE));

        // then
        assertThat(menuProduct.getPrice()).isEqualTo(new Price(BigDecimal.ONE));
    }
}
