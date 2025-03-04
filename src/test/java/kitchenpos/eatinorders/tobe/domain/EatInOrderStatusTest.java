package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.common.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EatInOrderStatusTest {

    @DisplayName("매장 내 주문과 관련 없는 주문 상태가 입력되면 예외가 발생한다")
    @EnumSource(value = OrderStatus.class, names = {"DELIVERING", "DELIVERED"}, mode = EnumSource.Mode.INCLUDE)
    @ParameterizedTest
    void from(OrderStatus status){
        assertThatThrownBy(() -> EatInOrderStatus.from(status))
                .isInstanceOf(InvalidOrderStatusException.class);
    }
}
