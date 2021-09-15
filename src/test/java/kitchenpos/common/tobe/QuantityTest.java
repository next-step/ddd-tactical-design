package kitchenpos.common.tobe;

import kitchenpos.commons.tobe.domain.model.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QuantityTest {

    @DisplayName("수량 생성 시 수량 값이 0 미만이면 IllegalArgumentException을 던진다.")
    @ValueSource(longs = -1)
    @ParameterizedTest
    void Quantity(final long quantity) {
        assertThatThrownBy(() -> new Quantity(quantity)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량 값이 필수고, 수량은 0 미만의 수량 값을 가질 수 없습니다");
    }

    @DisplayName("수량은 수량 값을 반환한다.")
    @ValueSource(longs = {0, 1})
    @ParameterizedTest
    void getQuantity(final long quantity) {
        final Quantity actual = new Quantity(quantity);
        assertThat(actual.value()).isEqualTo(quantity);
    }
}
