package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.Fixtures2.chicken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new MenuProduct(chicken(), 2));
    }

    @DisplayName("메뉴 상품의 수량은 0보다 작을 수 없다.")
    @Test
    void negativeQuantity() {
        assertThatThrownBy(() -> new MenuProduct(chicken(), -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 메뉴 상품의 수량 입니다.");
    }

    @DisplayName("메뉴 상품의 합산 가격을 구할 수 있다.")
    @Test
    void totalPrice() {
        MenuProduct menuProduct = new MenuProduct(chicken(), 2);

        assertThat(menuProduct.totalPrice()).isEqualTo(BigDecimal.valueOf(40000));
    }
}
