package kitchenpos.product.domain;

import static kitchenpos.product.Fixtures.VALID_NAME;
import static kitchenpos.product.Fixtures.VALID_PRODUCT_PRICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.product.Fixtures;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class ProductNewTest {


    @ParameterizedTest
    @NullSource
    void newOf_invalid_parameter_음식이름은_null일_수_없다(final ProductName value) {

        // when & then
        assertThatThrownBy(() -> ProductNew.create(value, VALID_PRODUCT_PRICE))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    void newOf_invalid_parameter_음식가격은_null일_수_없다(final ProductPrice value) {

        // when & then
        assertThatThrownBy(() -> ProductNew.create(ProductName.create(VALID_NAME), value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void newOf_음식이름과_음식가격을_받아서_productNew를_생성하여_반환한다() {
        // given
        final ProductName productName = ProductName.create(VALID_NAME);
        final ProductPrice productPrice = VALID_PRODUCT_PRICE;

        // when
        final ProductNew actual
            = ProductNew.create(productName, productPrice);

        // then
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getName()).isEqualTo(ProductName.create(VALID_NAME));
        assertThat(actual.getPrice()).isEqualTo(VALID_PRODUCT_PRICE);
    }

    @Test
    void changePrice_productNew의_음식가격을_변경한다() {
        // given
        final ProductNew product = Fixtures.create(1_000);

        // when
        product.changePrice(ProductPrice.create(3_000));

        // then
        assertThat(product.getPrice())
            .isEqualTo(ProductPrice.create(3_000));
    }
}