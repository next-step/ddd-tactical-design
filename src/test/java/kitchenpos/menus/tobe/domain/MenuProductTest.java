package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.products.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("메뉴 상품")
class MenuProductTest {

    @DisplayName("메뉴 상품 금액 계산")
    @Test
    void calculatePrice() {
        //given
        MenuProduct menuProduct = new MenuProduct(product(1000), 3);
        //then
        assertThat(menuProduct.getPrice().getValue())
                .isEqualTo(BigDecimal.valueOf(3000));
    }
}
