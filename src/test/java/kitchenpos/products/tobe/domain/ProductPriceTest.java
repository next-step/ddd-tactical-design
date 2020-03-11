package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @DisplayName("제품 가격을 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1000L, 1000000000L})
    void create(final Long price) {
        // given
        // when
        ProductPrice productPrice = new ProductPrice(price);

        // then
        assertThat(productPrice.toLong()).isEqualTo(price);
    }

    @DisplayName("제품 가격은 0원 이상이다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {-1L, -1000L, -1000000000L})
    void createOnlyWhenProductAdditionPolicySatisfied(final Long invalidPrice) {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new ProductPrice(invalidPrice);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
