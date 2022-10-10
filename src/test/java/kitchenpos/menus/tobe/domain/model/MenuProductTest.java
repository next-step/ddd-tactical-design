package kitchenpos.menus.tobe.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuProductTest {

    @DisplayName("가격 구하기")
    @Test
    void amount() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), 5, 1000L);

        assertThat(menuProduct.amount()).isEqualTo(5000L);
    }

    @DisplayName("상품 id 비교하기")
    @Test
    void matchProductId() {
        UUID productId = UUID.randomUUID();
        MenuProduct menuProduct = new MenuProduct(productId, 5, 1000L);

        assertThat(menuProduct.matchProductId(productId)).isTrue();
        assertThat(menuProduct.matchProductId(UUID.randomUUID())).isFalse();
    }
}