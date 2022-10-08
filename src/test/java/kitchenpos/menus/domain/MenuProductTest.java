package kitchenpos.menus.domain;

import static kitchenpos.menus.MenuFixtures.menu;
import static kitchenpos.menus.MenuFixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menus.exception.AlreadyEnrollMenuException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

    @DisplayName("가격에 수량이 곱해진 값을 구할 수 있다.")
    @Test
    void quantityMultipliedPrice() {
        MenuProduct menuProduct = new MenuProduct(
            UUID.randomUUID(),
            new MenuProductPrice(BigDecimal.valueOf(5_000)),
            new MenuProductQuantity(5)
        );

        assertThat(menuProduct.getQuantityMultipliedPrice()).isEqualTo(BigDecimal.valueOf(25_000));
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
