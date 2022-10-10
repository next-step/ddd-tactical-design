package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;

class ProductTest {
    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void register() {
        final ProductPrice price = new ProductPrice(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("chicken", new FakeProfanity());

        assertThatCode(() -> new Product(price, name))
                .doesNotThrowAnyException();
    }

    @DisplayName("가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final ProductPrice price = new ProductPrice(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("chicken", new FakeProfanity());

        Product product = new Product(price, name);
        product.changePrice(new ProductPrice(BigDecimal.ONE));
    }
}
