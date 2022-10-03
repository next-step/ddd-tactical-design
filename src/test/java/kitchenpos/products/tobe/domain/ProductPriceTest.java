package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @ParameterizedTest(name = "상품 가격은 비어있거나 음수일 수 없다. source = {0}")
    @MethodSource("provideArgumentsForConstructor")
    void constructor(Long price) {
        assertThatThrownBy(() -> new ProductPrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideArgumentsForConstructor() {
        return Stream.of(
                null,
                Arguments.of(-1000L)
        );
    }

    @DisplayName("상품 가격 동등성 비교")
    @Test
    void equals() {
        assertThat(new ProductPrice(9_000L)).isEqualTo(new ProductPrice(9_000L));
    }
}
