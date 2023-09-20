package kitchenpos.menu.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import kitchenpos.Fixtures;
import kitchenpos.product.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductTest {

    @DisplayName("메뉴 상품의 총 가격을 구한다")
    @Test
    void testCalculatePrice() {
        // given
        Product product = Fixtures.product();
        int quantity = 2;

        var menuProduct = Fixtures.menuProduct(product, quantity);

        // when
        var actual = menuProduct.calculatePrice();

        // then
        assertThat(actual).isEqualTo(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
    }
}
