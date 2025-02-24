package kitchenpos.menu.tobe.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MenuProductTest {

    private final UUID productId = UUID.randomUUID();

    @Test
    @DisplayName("MenuProduct 객체를 생성할 수 있다")
    void create() {
        // when
        MenuProduct menuProduct = new MenuProduct(1L, 2, 5000L, productId);

        // then
        assertThat(menuProduct.getQuantity()).isEqualTo(2);
        assertThat(menuProduct.getProductId()).isEqualTo(productId);
    }

    @Test
    @DisplayName("수량을 변경할 수 있다")
    void changeQuantity() {
        // given
        MenuProduct menuProduct = new MenuProduct(1L, 2, 5000L, productId);

        // when
        menuProduct.changeQuantity(3);

        // then
        assertThat(menuProduct.getQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("가격을 변경할 수 있다")
    void changePrice() {
        // given
        MenuProduct menuProduct = new MenuProduct(1L, 2, 5000L, productId);

        // when
        menuProduct.changePrice(6000L);

        // then
        assertThat(menuProduct.totalPrice()).isEqualTo(12000L);  // 6000 * 2
    }

    @Test
    @DisplayName("총 가격을 계산할 수 있다")
    void totalPrice() {
        // given
        MenuProduct menuProduct = new MenuProduct(1L, 2, 5000L, productId);

        // when
        Long totalPrice = menuProduct.totalPrice();

        // then
        assertThat(totalPrice).isEqualTo(10000L);  // 5000 * 2
    }
}