package kitchenpos.menu.tobe.domain;

import kitchenpos.menu.tobe.domain.menu.MenuProduct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

class MenuProductTest {

    @Test
    @DisplayName("메뉴 상품을 정상적으로 생성할 수 있다.")
    void test1() {
        UUID productId = UUID.randomUUID();
        long quantity = 2L;
        BigDecimal price = BigDecimal.valueOf(1000);

        MenuProduct menuProduct = new MenuProduct(productId, quantity, price);

        Assertions.assertThat(menuProduct).isNotNull();
        Assertions.assertThat(menuProduct.getProductId()).isEqualTo(productId);
        Assertions.assertThat(menuProduct.getQuantity()).isEqualTo(quantity);
        Assertions.assertThat(menuProduct.getPrice()).isEqualTo(price);
    }

}