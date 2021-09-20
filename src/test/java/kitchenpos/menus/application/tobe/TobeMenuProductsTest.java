package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.TobeMenuProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.menus.application.tobe.TobeMenusFixtures.menuProducts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TobeMenuProductsTest {

    @DisplayName("상품들 생성")
    @Test
    void create() {
        MenuProducts menuProductList = menuProducts();

        assertThat(menuProductList).isNotNull();
    }

    @DisplayName("상품들 개수 확인")
    @Test
    void quantity() {
        MenuProducts menuProductList = menuProducts();

        assertThat(menuProductList.getMenuProducts().size()).isEqualTo(2);
    }

    @DisplayName("상품들예외")
    @Test
    void nagetiveQuantity() {
        assertThatThrownBy(
                () -> menuProducts(null)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품들의 총 금액 확인")
    @Test
    void menuProductPrice() {
        MenuProducts menuProducts = menuProducts();

        assertThat(menuProducts.sumMenuProductPrice()).isEqualTo(BigDecimal.valueOf(33_000L));
    }
}