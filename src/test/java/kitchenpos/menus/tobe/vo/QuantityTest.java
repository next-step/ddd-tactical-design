package kitchenpos.menus.tobe.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴 CONTEXT 수량")
class QuantityTest {

    @DisplayName("수량 생성")
    @Test
    void createQuantity() {
        Quantity quantity = new Quantity(2);

        assertThat(quantity).isEqualTo(new Quantity(2));
    }

    @DisplayName("수량이 0보다 작으면 에러")
    @ValueSource(longs = {-1L, -10L})
    @ParameterizedTest
    void quantityNegative(long value) {
        assertThatThrownBy(() -> new Quantity(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
