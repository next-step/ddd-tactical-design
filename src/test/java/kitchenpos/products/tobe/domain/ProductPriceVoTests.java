package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceVoTests {
    @DisplayName("값을 0원 이상으로 상품 가격 책정 시 성공")
    @ParameterizedTest
    @MethodSource("validPrice")
    public void valueOfProductPriceWithValidValue(BigDecimal price) {
        ProductPrice productPrice = ProductPrice.valueOf(price);

        assertThat(productPrice.checkProductPriceValue()).isEqualTo(price);
    }
    private static Stream<Arguments> validPrice() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(16000)),
                Arguments.of(BigDecimal.valueOf(10000))
        );
    }

    @DisplayName("값을 0원 미안으로 상품 가격 책정 시 성공")
    @ParameterizedTest
    @NullSource
    @MethodSource("invalidPrices")
    public void valueOfProductPriceWithInValidValue(BigDecimal price) {
        assertThatThrownBy(() -> {
            ProductPrice.valueOf(price);
        }).isInstanceOf(IllegalArgumentException.class);
    }
    private static Stream<Arguments> invalidPrices() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(-1)),
                Arguments.of(BigDecimal.valueOf(-2))
        );
    }
}
