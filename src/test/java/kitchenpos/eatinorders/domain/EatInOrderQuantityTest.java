package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderQuantity;

class EatInOrderQuantityTest {

    @DisplayName("주문량을 0이하로 할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -13000})
    void of1(int quantity) {
        assertThatNoException().isThrownBy(() -> EatInOrderQuantity.of(quantity, OrderType.EAT_IN));
    }
}
