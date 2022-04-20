package kitchenpos.menus.domain;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.Arrays;
import kitchenpos.menus.tobe.domain.MenuPrice;
import kitchenpos.menus.tobe.domain.MenuProducts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
    메뉴 가격은 메뉴의 속한 상품 가격의 합과 작거나 같아야 한다.
    메뉴 가격은 0보다 작을 수 없다.
 */
class MenuPriceTest {

    @DisplayName("메뉴 가격이 메뉴에 속한 상품의 가격의 합보다 작다면 생성 할 수 있다.")
    @Test
    void under_menu_products_price() {
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(product(), product()));

        assertAll(
                () -> assertDoesNotThrow(() -> new MenuPrice(BigDecimal.TEN, menuProducts.totalPrice())),
                () -> assertThat(new MenuPrice(BigDecimal.TEN, menuProducts.totalPrice()))
                        .isInstanceOf(MenuPrice.class)
        );
    }

    @DisplayName("메뉴 가격이 메뉴에 속한 상품의 가격의 합과 같다면 생성 할 수 있다.")
    @Test
    void same_menu_products_price() {
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(product(), product()));

        assertAll(
                () -> assertDoesNotThrow(() -> new MenuPrice(BigDecimal.valueOf(32_000), menuProducts.totalPrice())),
                () -> assertThat(new MenuPrice(BigDecimal.valueOf(32_000), menuProducts.totalPrice()))
                        .isInstanceOf(MenuPrice.class)
        );
    }

    @DisplayName("메뉴에 속한 상품의 가격의 합 보다 크면 메뉴 가격을 생성할 수 없다.")
    @Test
    void over_menu_products_price() {
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(product(), product()));

        assertThatThrownBy(() -> new MenuPrice(BigDecimal.valueOf(1_000_000_000L), menuProducts.totalPrice()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 가격이 0보다 작다면 생성할 수 없다.")
    @ParameterizedTest
    @ValueSource(longs = {-100, -1})
    void menu_price_under_zero(final long price) {
        final MenuProducts menuProducts = new MenuProducts(Arrays.asList(product(), product()));

        assertThatThrownBy(() -> new MenuPrice(BigDecimal.valueOf(price), menuProducts.totalPrice()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
