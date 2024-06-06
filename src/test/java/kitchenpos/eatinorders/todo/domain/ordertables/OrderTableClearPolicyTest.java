package kitchenpos.eatinorders.todo.domain.ordertables;

import kitchenpos.eatinorders.exception.KitchenPosIllegalStateException;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderRepository;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static kitchenpos.eatinorders.todo.domain.ordertables.OrderTable.OCCUPIED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;

@DisplayName("주문테이블 초기화 정책")
@ExtendWith(MockitoExtension.class)
class OrderTableClearPolicyTest {
    private static final OrderTableName orderTableName = OrderTableName.from("1번");
    private static final NumberOfGuests numberOfGuests = NumberOfGuests.from(3);

    @Mock
    private EatInOrderRepository orderRepository;
    @InjectMocks
    private OrderTableClearPolicy orderTableClearPolicy;

    private UUID orderTableId;

    @BeforeEach
    void setUp() {
        orderTableId = OrderTable.from(orderTableName, numberOfGuests, OCCUPIED).getId();
    }

    @DisplayName("[성공] 주문테이블을 초기화 하기에 유효한 주문들만 포함되면 예외가 발생하지 않는다.")
    @Test
    void checkClear() {
        given(orderRepository.existsByOrderTableIdAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED)).willReturn(false);

        assertDoesNotThrow(() -> orderTableClearPolicy.checkClear(orderTableId));
    }

    @DisplayName("[실패] 주문테이블을 초기화 하기에 유효하지 않은 주문이 포함되면 예외가 발생한다.")
    @Test
    void fail_checkClear() {
        given(orderRepository.existsByOrderTableIdAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED)).willReturn(true);

        assertThatThrownBy(() -> orderTableClearPolicy.checkClear(orderTableId))
                .isInstanceOf(KitchenPosIllegalStateException.class);
    }
}
