package kitchenpos.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class ProductPriceTest {


    @ParameterizedTest
    @ValueSource(longs = -1)
    void of_가격은_음수일_수_없다(final long value) {

        // when & then
        assertThatThrownBy(() -> ProductPrice.of(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @ValueSource(longs = {0, 1})
    void of_productPrice를_생성하여_반환한다(final long value) {

        // when
        final ProductPrice actual = ProductPrice.of(value);

        // then
        final ProductPrice expected = ProductPrice.of(value);
        assertThat(actual).isEqualTo(expected);
    }
}