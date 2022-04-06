package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.Fixtures2.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MenuProductsTest {

    @DisplayName("MenuProducts를 생성할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new MenuProducts(chickenMenuProduct(), frenchFriesMenuProduct()));
    }

    @DisplayName("메뉴 상품이 없으면 등록할 수 없다.")
    @Test
    void emtpyProduct() {
        assertThatThrownBy(MenuProducts::new)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메뉴 상품 정보가 존재하지 않습니다.");
    }

    @DisplayName("상품의 합산 금액을 구할 수 있다.")
    @Test
    void totalPrice() {
        MenuProducts menuProducts = new MenuProducts(chickenMenuProductWithQuantity(2), frenchFriesMenuProductWithQuantity(3));

        assertThat(menuProducts.totalPrice()).isEqualTo(BigDecimal.valueOf(55000));
    }
}
