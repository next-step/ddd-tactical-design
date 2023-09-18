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
    @ValueSource(ints = -1)
    void of_가격은_음수일_수_없다(final int value) {

        // when & then
        assertThatThrownBy(() -> ProductPrice.create(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void of_productPrice를_생성하여_반환한다(final int value) {

        // when
        final ProductPrice actual = ProductPrice.create(value);

        // then
        final ProductPrice expected = ProductPrice.create(value);
        assertThat(actual).isEqualTo(expected);
    }
}