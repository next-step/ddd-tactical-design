package kitchenpos.menus.tobe.domain;

import static kitchenpos.menus.tobe.MenuFixtures.menuProduct;
import static kitchenpos.menus.tobe.MenuFixtures.price;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성한다.")
    @Test
    void create() {
        long quantity = 2;
        long productPrice = 1;
        MenuProduct menuProduct = menuProduct(quantity, productPrice);

        assertThat(menuProduct).isNotNull();
        assertThat(menuProduct.getPrice()).isEqualTo(price(2));
    }
}
