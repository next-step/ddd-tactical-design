package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @DisplayName("제품 가격을 생성할 수 있다.")
    @ParameterizedTest
    @MethodSource(value = "provideValidPrice")
    void create(final BigDecimal price) {
        // given
        // when
        ProductPrice productPrice = new ProductPrice(price);

        // then
        assertThat(productPrice.get()).isEqualTo(price);
    }

    private static Stream provideValidPrice() {
        return Stream.of(
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(1000000000)
        );
    }

    @DisplayName("제품 가격은 0원 이상이다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideInvalidPrice")
    void createOnlyWhenProductAdditionPolicySatisfied(final BigDecimal invalidPrice) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new ProductPrice(invalidPrice);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideInvalidPrice() {
        return Stream.of(
                BigDecimal.valueOf(-1),
                BigDecimal.valueOf(-1000),
                BigDecimal.valueOf(-1000000000)
        );
    }
}
