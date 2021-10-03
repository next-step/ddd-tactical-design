package kitchenpos.menus.tobe.menu.domain.model;

import static kitchenpos.fixture.MenuProductFixture.MENU_PRODUCT1;
import static kitchenpos.fixture.MenuProductFixture.MENU_PRODUCT2;
import static kitchenpos.fixture.MenuProductsFixture.MENU_PRODUCTS;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {

    @DisplayName("MenuProduct들로 MenuProducts를 생성할 수 있다")
    @Test
    void test() {
        assertThat(MENU_PRODUCTS()).isNotNull();
    }

    @DisplayName("MenuProducts를 생성할 수 있다")
    @Test
    void calculateTotalPrice() {
        final MenuProducts menuProducts = MENU_PRODUCTS();

        assertThat(menuProducts.calculateTotalPrice()).isEqualTo(MENU_PRODUCT1().getMenuProductPrice()
            .add(MENU_PRODUCT2().getMenuProductPrice()));
    }
}
