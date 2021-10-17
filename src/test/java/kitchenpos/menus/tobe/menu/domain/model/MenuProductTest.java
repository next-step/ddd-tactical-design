package kitchenpos.menus.tobe.menu.domain.model;

import static kitchenpos.fixture.MenuProductFixture.MENU_PRODUCT1;
import static kitchenpos.fixture.ProductFixture.PRODUCT1;
import static org.assertj.core.api.Assertions.assertThat;

import kitchenpos.products.tobe.domain.model.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

    @DisplayName("quantity x productPrice의 값을 얻을 수 있다")
    @Test
    void getMenuProductPrice() {
        final MenuProduct menuProduct = MENU_PRODUCT1();
        final ProductPrice menuProductPrice = menuProduct.getMenuProductPrice();

        assertThat(menuProductPrice).isEqualTo(PRODUCT1().getPrice()
            .multiply(new Quantity(2L)));
    }

}
