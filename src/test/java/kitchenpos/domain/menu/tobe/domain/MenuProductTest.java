package kitchenpos.domain.menu.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성한다")
    @Test
    void constructor() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), BigDecimal.ZERO, 0L);
        assertThat(menuProduct.price()).isZero();
        assertThat(menuProduct.quantity()).isZero();
    }

    @DisplayName("메뉴 상품의 가격이 Null이면 생성을 실패한다")
    @Test
    void constructor_price_null_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuProduct(UUID.randomUUID(), null, 0L));
    }

    @DisplayName("메뉴 상품의 가격이 음수이면 생성을 실패한다")
    @Test
    void constructor_price_minus_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(-1), 0L));
    }

    @DisplayName("메뉴 상품의 수량이 음수이면 생성을 실패한다")
    @Test
    void constructor_quantity_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuProduct(UUID.randomUUID(), BigDecimal.ZERO, -1L));
    }
}
