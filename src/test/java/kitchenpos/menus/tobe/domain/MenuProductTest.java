package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductTest {


    @Test
    @DisplayName("메뉴에 등록할 상품을 만들 수 있다.")
    void create_menu_product() {
        UUID productId = UUID.randomUUID();
        MenuProduct menuProduct = new MenuProduct(productId, new MenuProductPrice(1000L), new MenuQuantity(2));

        MenuProductPrice price = new MenuProductPrice(1000L);
        MenuQuantity quantity = new MenuQuantity(2);


        assertThat(menuProduct.getProductId()).isEqualTo(productId);
        assertThat(menuProduct.getPrice()).isEqualTo(price);
        assertThat(menuProduct.getQuantity()).isEqualTo(quantity);

    }

    @Test
    @DisplayName("메뉴에 속한 상품의 수량은 0이상 이어야 합니다.")
    void can_not_create_menu_product_quantity_less_then_0() {
        assertThatThrownBy(() -> new MenuProduct(UUID.randomUUID(), new MenuProductPrice(1000L), new MenuQuantity(0)))
                .isInstanceOf(IllegalArgumentException.class);

    }
}