package kitchenpos.menus.domain;

import static kitchenpos.menus.MenuFixtures.menu;
import static kitchenpos.menus.MenuFixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import kitchenpos.menus.exception.AlreadyEnrollMenuException;
import kitchenpos.menus.exception.InvalidMenuProductsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {

    @DisplayName("메뉴의 상품은 하나 이상이어야 한다.")
    @Test
    void emptyException() {
        assertThatThrownBy(() -> new MenuProducts(new ArrayList<>()))
            .isExactlyInstanceOf(InvalidMenuProductsException.class);
    }

    @DisplayName("메뉴의 상품에 메뉴를 등록할 때 이미 메뉴가 등록되어 있는 경우 예외가 발생한다.")
    @Test
    void alreadyEnrollMenuException() {
        MenuProduct menuProduct = menuProduct();
        menuProduct.enrollMenu(menu());
        assertThatThrownBy(() -> menuProduct.enrollMenu(menu()))
            .isExactlyInstanceOf(AlreadyEnrollMenuException.class);
    }
}
