package kitchenpos.menus.tobe.domain;

import static kitchenpos.global.TobeFixtures.menuProduct;
import static kitchenpos.global.TobeFixtures.price;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성한다.")
    @Test
    void create() {
        long quantity = 2;
        BigDecimal productPrice = BigDecimal.ONE;
        MenuProduct menuProduct = menuProduct(quantity, productPrice);

        assertThat(menuProduct).isNotNull();
        assertThat(menuProduct.getPrice()).isEqualTo(price(BigDecimal.valueOf(2)));
    }
}
