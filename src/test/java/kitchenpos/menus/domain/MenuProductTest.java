package kitchenpos.menus.domain;

import kitchenpos.products.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuProductTest {

    @DisplayName("메뉴 상품의 가격이 유효하지 않다")
    @ParameterizedTest()
    @ValueSource(longs = {-1L, -2L})
    void invalid_price_menu_product(long price){
        assertThatThrownBy(() -> new MenuProduct(product().getId(), BigDecimal.valueOf(price), 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 상품의 수량이 유효하지 않다")
    @ParameterizedTest()
    @ValueSource(longs = {-1L, -2L})
    void invalid_quantity_menu_product(long quantity){
        Product product = product();
        assertThatThrownBy(() -> new MenuProduct(product.getId(), product.getPrice(), quantity))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void menu_product_price_calculation() {
        MenuProduct menuProduct = menuProduct(product("양념치킨", 16000L), 2L);
        assertThat(menuProduct.calculatePrice().compareTo(BigDecimal.valueOf(32000L))).isEqualTo(0);
    }
}
