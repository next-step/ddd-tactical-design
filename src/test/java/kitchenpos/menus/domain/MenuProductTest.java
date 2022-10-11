package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.UUID;
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
}
