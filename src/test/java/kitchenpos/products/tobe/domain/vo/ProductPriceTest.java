package kitchenpos.products.tobe.domain.vo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.exception.MinimumProductPriceException;
import kitchenpos.products.tobe.domain.exception.NullProductPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

    @DisplayName("가격을 등록할 수 있다.")
    @Test
    void create() {
        ProductPrice expected = new ProductPrice(BigDecimal.ZERO);

        assertThat(expected).isEqualTo(new ProductPrice(BigDecimal.ZERO));
        assertThat(expected.hashCode() == new ProductPrice(BigDecimal.ZERO).hashCode())
                .isTrue();
    }

    @DisplayName("상품 가격 에러 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("반드시 값이 존재해야 한다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @NullSource
        void error1(BigDecimal price) {
            assertThatExceptionOfType(NullProductPriceException.class)
                    .isThrownBy(() -> new ProductPrice(price));
        }

        @DisplayName("반드시 0원 이상이어야 한다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = "-1")
        void error2(BigDecimal price) {
            assertThatExceptionOfType(MinimumProductPriceException.class)
                    .isThrownBy(() -> new ProductPrice(price));
        }
    }
}
