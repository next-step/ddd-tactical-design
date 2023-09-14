package kitchenpos.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class ProductNameTest {


    @ParameterizedTest
    @NullSource
    void of_name이_null이면_예외를_반환한다(final Name value) {

        // when & then
        assertThatThrownBy(() -> ProductName.of(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @ValueSource(strings = "dummy")
    void of_name의_값으로_productName을_만들어_반환한다(final String value) {

        // given
        final Name name = new Name(value);

        // when
        final ProductName actual = ProductName.of(name);

        // then
        assertThat(actual)
            .extracting("value")
            .isEqualTo(value);
    }
}