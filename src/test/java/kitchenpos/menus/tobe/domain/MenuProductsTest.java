package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import kitchenpos.menus.TobeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {
    @DisplayName("금액의 총합을 반환")
    @Test
    void totalAmount() {
        // given
        MenuProducts menuProducts = new MenuProducts(TobeFixtures.menuProduct(), TobeFixtures.menuProduct());

        // when
        BigDecimal totalAmount = menuProducts.getTotalAmount();

        // then
        assertThat(totalAmount).isEqualTo(
            new BigDecimal(TobeFixtures.MENU_PRODUCT_QUANTITY * TobeFixtures.MENU_PRODUCT_PRICE * 2)
        );
    }
}
