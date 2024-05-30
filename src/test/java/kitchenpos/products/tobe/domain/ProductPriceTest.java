package kitchenpos.products.tobe.domain;

import kitchenpos.products.exception.InvalidProductPriceException;
import kitchenpos.support.domain.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("상품 가격")
class ProductPriceTest {
    @DisplayName("[성공] 상품가격을 생성한다.")
    @Test
    void create() {
        ProductPrice price = ProductPrice.from(BigDecimal.valueOf(10_000));

        assertThat(price).isEqualTo(ProductPrice.from(10_000));
    }

    @DisplayName("[실패] 상품의 가격은 0원 이상이어야 한다.")
    @ValueSource(strings = {"-1", "-1000", "-9999999"})
    @NullSource
    @ParameterizedTest
    void fail1(BigDecimal price) {
        assertThatThrownBy(() -> ProductPrice.from(price))
                .isInstanceOf(InvalidProductPriceException.class);
    }

    @DisplayName("[성공] 상품의 가격에 숫자를 곱한다.")
    @Test
    void success1() {
        ProductPrice price = ProductPrice.from(10_000);

        ProductPrice actual = price.multiply(BigDecimal.TEN);

        assertThat(actual).isEqualTo(ProductPrice.from(100_000));
    }

    @DisplayName("[실패] 상품의 가격에 숫자를 곱한 결과는 0원 이상이어야 한다.")
    @ValueSource(strings = {"-1", "-2", "-10"})
    @ParameterizedTest
    void fail2(BigDecimal input) {
        ProductPrice price = ProductPrice.from(10_000);

        assertThatThrownBy(() -> price.multiply(input))
                .isInstanceOf(InvalidProductPriceException.class);
    }
}
