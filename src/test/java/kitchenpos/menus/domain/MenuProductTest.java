package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.Fixtures;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductTest {

    @DisplayName("메뉴 상품의 수량이 0보다 작다면 생성하지 못한다.")
    @ParameterizedTest
    @ValueSource(longs = {-10, -1})
    void menu_product_without_product(long quantity) {
        Product product = Fixtures.product();
        assertThatThrownBy(() -> new MenuProduct(product, quantity)).isInstanceOf(IllegalArgumentException.class);
    }
}
