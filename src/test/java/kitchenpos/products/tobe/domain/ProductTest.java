package kitchenpos.products.tobe.domain;

import kitchenpos.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    @Test
    @DisplayName("상품을 생성할 수 있다.")
    void create() {
        assertDoesNotThrow((ThrowingSupplier<Product>) ProductFixture::상품);
    }

    @ParameterizedTest
    @ValueSource(strings = "100")
    @DisplayName("상품의 가격을 변경할 수 있다.")
    void changePrice(final BigDecimal price) {
        final Product product = ProductFixture.상품();
        final Product changeProduct = ProductFixture.상품(price);

        product.changePrice(changeProduct);

        assertThat(product.getPrice()).isEqualTo(changeProduct.getPrice());
    }

}