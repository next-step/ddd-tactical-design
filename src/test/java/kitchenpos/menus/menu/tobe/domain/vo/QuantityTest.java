package kitchenpos.menus.menu.tobe.domain.vo;

import kitchenpos.menus.menu.tobe.domain.vo.exception.InvalidQuantityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    @DisplayName("수량을 생성한다.")
    @Test
    void valueOf() {
        final Quantity quantity = Quantity.valueOf(1L);

        assertAll(
                () -> assertThat(quantity).isEqualTo(Quantity.valueOf(1L)),
                () -> assertThat(quantity.value()).isEqualTo(1L)
        );
    }

    @ParameterizedTest(name = "수량은 0개 이상이여야 한다. quantity={0}")
    @NullSource
    @ValueSource(longs = {-1L, -2L})
    void invalid_price(final Long quantity) {
        assertThatThrownBy(() -> Quantity.valueOf(quantity))
                .isInstanceOf(InvalidQuantityException.class)
                .hasMessage("수량은 0개 이상이여야 합니다. value=" + quantity);
    }

}
