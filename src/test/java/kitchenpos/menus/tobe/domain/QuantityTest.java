package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("수량 테스트")
class QuantityTest {

    @DisplayName("수량을 생성할 수 있다.")
    @ParameterizedTest
    @ValueSource(longs = {0, 10, 100})
    void createQuantity(long value) {
        Quantity quantity = Quantity.from(value);
        assertThat(quantity).isEqualTo(Quantity.from(value));
    }

    @Test
    @DisplayName("수량은 0 이상이어야 한다.")
    void createQuantity_GreaterThanZero() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Quantity.from(-1));
    }
}