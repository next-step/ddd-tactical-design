package kitchenpos.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class NameTest {

    @ParameterizedTest
    @NullAndEmptySource
    void constructor_이름은_비어있을_수_없다(final String value) {

        // when & then
        assertThatThrownBy(() -> new Name(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @ValueSource(strings = "dummy")
    void constructor_이름을_생성하여_반환한다(final String value) {

        // when
        final Name actual = new Name(value);

        // then
        final Name expected = new Name(value);
        assertThat(actual).isEqualTo(expected);
    }
}