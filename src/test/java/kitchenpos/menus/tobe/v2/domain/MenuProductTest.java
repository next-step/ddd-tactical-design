package kitchenpos.menus.tobe.v2.domain;

import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MenuProductTest {

    @DisplayName("메뉴 상품 정상 생성")
    @Test
    void create() {
        Product product = new Product("후라이드", BigDecimal.TEN);
        long quantity = 1L;

        MenuProduct menuProduct = new MenuProduct(product, quantity);

        assertThat(menuProduct.getQuantity()).isEqualTo(quantity);
    }
}
