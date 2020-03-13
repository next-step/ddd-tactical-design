package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuProductTest {

    @DisplayName("메뉴 상품 정상 생성")
    @Test
    void create() {
        long quantity = 1L;

        MenuProduct menuProduct = new MenuProduct(quantity);

        assertThat(menuProduct.getQuantity()).isEqualTo(quantity);
    }
}
