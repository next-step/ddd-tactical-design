package kitchenpos.ordermaster.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.eatinorders.domain.OrderType;

class OrderQuantityTest {

    @DisplayName("매장 식사는, 주문량을 0이하로 할 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, -13000})
    void of1(int quantity) {
        assertThatNoException().isThrownBy(() -> OrderQuantity.of(quantity, OrderType.EAT_IN));
    }

    @ParameterizedTest
    @EnumSource(value = OrderType.class, names = "EAT_IN", mode = EnumSource.Mode.EXCLUDE)
    void of2(OrderType orderType) {
        assertThatThrownBy(() -> OrderQuantity.of(-1, orderType))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("수량은 0보다 작을 수 없습니다.");

    }
}
