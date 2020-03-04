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

class ProductDomainTests {
    @DisplayName("가격이 0원 이상인 상품 생성 시 성공")
    @ParameterizedTest
    @MethodSource("validPrices")
    public void createProductWithValidPrice(BigDecimal price) {
        final Product product = Product.registerProduct("후라이드치킨", price);

        assertThat(product.getName()).isEqualTo("후라이드치킨");
        assertThat(product.getPrice()).isEqualTo(price);
    }
    private static Stream<Arguments> validPrices() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(16000)),
                Arguments.of(BigDecimal.valueOf(10000))
        );
    }

    @DisplayName("가격이 0원 미만인 상품 생성 시 실패")
    @ParameterizedTest
    @NullSource
    @MethodSource("invalidPrices")
    public void createProductWithInvalidPrice(BigDecimal price) {
        assertThatThrownBy(() -> {
            Product.registerProduct("후라이드치킨", price);
        }).isInstanceOf(IllegalArgumentException.class);
    }
    private static Stream<Arguments> invalidPrices() {
        return Stream.of(
                Arguments.of(BigDecimal.valueOf(-1)),
                Arguments.of(BigDecimal.valueOf(-2))
        );
    }
}
