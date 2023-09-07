package kitchenpos.products.tobe.domain;

import kitchenpos.products.exception.ProductErrorCode;
import kitchenpos.products.exception.ProductPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("상품 가격")
class ProductPriceTest {


    @DisplayName("가격 생성")
    @Nested
    class create {

        @DisplayName("[성공] 상품 가격을 생성한다")
        @Test
        void create1() {
            assertThatNoException().isThrownBy(
                    () -> new ProductPrice(BigDecimal.TEN)
            );
        }

        @DisplayName("[실패] 상품 가격은 0 이상이다")
        @ParameterizedTest
        @ValueSource(strings = {"-10", "-200"})
        void create2(BigDecimal price) {
            assertThatThrownBy(
                    () -> new ProductPrice(price))
                    .isInstanceOf(ProductPriceException.class)
                    .hasMessage(ProductErrorCode.PRICE_IS_GREATER_THAN_EQUAL_ZERO.getMessage());
        }

        @DisplayName("[실패] 상품 가격은 null일 수 없다")
        @ParameterizedTest
        @NullSource
        void create3(BigDecimal price) {
            assertThatThrownBy(
                    () -> new ProductPrice(price))
                    .isInstanceOf(ProductPriceException.class)
                    .hasMessage(ProductErrorCode.PRICE_IS_NULL.getMessage());
        }
    }

    @DisplayName("더하기")
    @Test
    void add() {
        ProductPrice ten = createProductPrice(10);
        ProductPrice hundred = createProductPrice(100);
        ProductPrice add = ten.add(hundred);
        assertThat(add).isEqualTo(createProductPrice(110));
    }

    @DisplayName("곱하기")
    @Test
    void multiply() {
        ProductPrice ten = createProductPrice(10);
        ProductPrice multiply = ten.multiply(10);
        assertThat(multiply).isEqualTo(createProductPrice(100));
    }

    private ProductPrice createProductPrice(long input) {
        return new ProductPrice(BigDecimal.valueOf(input));
    }
}
