package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kitchenpos.Fixtures;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class MenuProductsTest {

    @DisplayName("메뉴 상품 생성")
    @Test
    void create_menu_products() {
        List<Product> products = Arrays.asList(Fixtures.product());
        assertAll(
                () -> assertDoesNotThrow(() -> new MenuProducts(products)),
                () -> assertThat(new MenuProducts(products)).isInstanceOf(MenuProducts.class)
        );
    }

    @DisplayName("메뉴에 속한 상품 목록이 0개 미만이라면 생성할 수 없다.")
    @Test
    void menu_products_size_under_zero() {
        assertThatThrownBy(() -> new MenuProducts(Collections.emptyList()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 속한 상품 목록이 null 이라면 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    void menu_products_is_null(List products) {
        assertThatThrownBy(() -> new MenuProducts(products))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
