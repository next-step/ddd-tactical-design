package kitchenpos.product.domain.model;

import kitchenpos.product.domain.exception.ProductPriceValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductPriceTest {

    @DisplayName("`Product Price`를 생성할 수 있다")
    @Test
    void create() {
        // given
        final BigDecimal price = BigDecimal.valueOf(1000);

        // when
        final ProductPrice productPrice = ProductPrice.of(price);

        // then
        assertThat(productPrice.value()).isEqualTo(price);
    }

    @DisplayName("`Product Price`는 0원 이상 입력하여야 한다")
    @Test
    void value() {
        // given
        final BigDecimal price = BigDecimal.valueOf(-1);

        // when
        final Throwable thrown = catchThrowable(() -> ProductPrice.of(price));

        // then
        assertThat(thrown).isInstanceOf(ProductPriceValidationException.class);
    }
}