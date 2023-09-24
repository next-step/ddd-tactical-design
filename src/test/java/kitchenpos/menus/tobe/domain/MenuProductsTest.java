package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.exception.MenuProductException;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static kitchenpos.menus.application.fixtures.MenuFixture.menuProduct;
import static kitchenpos.products.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴 상품들 테스트")
class MenuProductsTest {

    @DisplayName("가격 합산")
    @Test
    void calculate1() {
        //given
        MenuProduct menuProduct_1000 = menuProduct(product(1000), 3);
        MenuProduct menuProduct_2000 = menuProduct(product(2000), 3);
        //when
        MenuProducts menuProducts = new MenuProducts(Arrays.asList(menuProduct_1000, menuProduct_2000));
        //then
        assertThat(menuProducts.calculateSum())
                .isEqualTo(BigDecimal.valueOf(9000));
    }

    @DisplayName("[실패] 메뉴 상품은 1개 이상이어야 한다.")
    @Test
    void create() {
        assertThatThrownBy(() -> new MenuProducts(Collections.emptyList()))
                .isInstanceOf(MenuProductException.class);
    }
}
