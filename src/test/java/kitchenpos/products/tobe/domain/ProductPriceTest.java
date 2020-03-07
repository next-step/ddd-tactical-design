package kitchenpos.products.tobe.domain;

import org.assertj.core.api.Assertions;
import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @DisplayName("제품 가격을 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(longs = { 0L, 1000L, 1000000000L })
    void create(final Long price) {
        // given
        // when
        ProductPrice productPrice = new ProductPrice(price);

        // then
        assertThat(productPrice.toLong()).isEqualTo(price);
    }

    @DisplayName("제품 가격이 0원 이상이여야한다.")
    @ParameterizedTest
    @MethodSource(value = "provideInvalidPrice")
    void createFails(final Long price) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
                new ProductPrice(price);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideInvalidPrice() {
        return Stream.of(
                null,
                -1L,
                -1000L,
                -1000000000L
        );
    }
}
