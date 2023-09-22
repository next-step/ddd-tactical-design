package kitchenpos.menu.domain.menu;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class MenuPriceTest {

    @ParameterizedTest
    @ValueSource(ints = -1)
    void create_value가_음수면_예외를_발생시킨다(final int value) {

        // when & then
        assertThatThrownBy(() -> MenuPrice.create(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}