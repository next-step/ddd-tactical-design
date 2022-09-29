package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import kitchenpos.TobeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {
    @DisplayName("가격과 수량을 곱하여 금액을 반환")
    @Test
    void getAmount() {
        // given
        MenuProduct menuProduct = TobeFixtures.menuProduct();

        // when
        BigDecimal amount = menuProduct.getAmount();

        // then
        assertThat(amount).isEqualTo(
            new BigDecimal(TobeFixtures.MENU_PRODUCT_QUANTITY * TobeFixtures.MENU_PRODUCT_PRICE)
        );
    }
}
