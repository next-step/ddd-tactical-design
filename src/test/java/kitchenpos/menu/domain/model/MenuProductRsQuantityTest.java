package kitchenpos.menu.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductRsQuantityTest {

    @Test
    @DisplayName("메뉴 상품의 수량이 1 이상이 아니면 예외를 던진다.")
    void create_menu_product_quantity_exception() {
        // given
        int quantity = 0;

        // when // then
        assertThatThrownBy(() -> new MenuProductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 상품의 수량은 0보다 커야 합니다!");
    }
}
