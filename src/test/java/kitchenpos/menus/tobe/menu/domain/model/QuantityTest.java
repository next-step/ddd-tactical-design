package kitchenpos.menus.tobe.menu.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.menus.tobe.menu.exception.WrongQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {

    @DisplayName("음수면_exception이_발생한다")
    @Test
    void wrongQuantityException() {
        assertThatThrownBy(
            () -> new Quantity(-1L)
        ).isInstanceOf(WrongQuantityException.class);
    }

    @DisplayName("같은 값이면 equals의 결과도 같다")
    @ParameterizedTest
    @ValueSource(longs = {0L, 1L, 1000L, Long.MAX_VALUE})
    void equals(final long value) {
        final Quantity quantity1 = new Quantity(value);
        final Quantity quantity2 = new Quantity(value);
        assertThat(quantity1).isEqualTo(quantity2);
    }

}
