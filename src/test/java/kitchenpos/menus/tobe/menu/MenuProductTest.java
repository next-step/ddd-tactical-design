package kitchenpos.menus.tobe.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

class MenuProductTest {

    @DisplayName("메뉴 상품 상품, 수량을 가진다.")
    @Test
    void constructor01() {
        assertThatCode(() ->
                new MenuProduct(UUID.randomUUID(), 10, BigDecimal.ONE)
        ).doesNotThrowAnyException();
    }

    @DisplayName("상품의 수량은 0 미만일 수 없다.")
    @Test
    void constructor02() {
        assertThatThrownBy(() ->
                new MenuProduct(UUID.randomUUID(), -1, BigDecimal.ONE)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 금액을 계산할 수 있다")
    @Test
    void test01() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), 3, BigDecimal.TEN);

        assertThat(menuProduct.amount()).isEqualTo(30);
    }
}
