package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.fixture.EatIntOrderFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EatInOrderTest {

    @Test
    @DisplayName("주문을 생성할 수 있다.")
    void create() {
        // given
        UUID orderTableId = UUID.randomUUID();
        OrderLineItems orderLineItems = EatIntOrderFixture.createOrderLineItems(3);

        // when
        EatInOrder eatInOrder = EatInOrder.startWaiting(orderTableId, orderLineItems);

        assertNotNull(eatInOrder);
        assertEquals(orderTableId, eatInOrder.getOrderTableId());
        assertEquals(EatInOrderStatus.WAITING, eatInOrder.getStatus());
        assertEquals(3, eatInOrder.getOrderLineItems().size());
    }

    @Test
    @DisplayName("주문을 수락할 수 있다.")
    void accept() {
        // given
        UUID orderTableId = UUID.randomUUID();
        EatInOrder eatInOrder = EatIntOrderFixture.createEatInOrder(orderTableId);
        EatInOrderStatus beforeStatus = eatInOrder.getStatus();
        // when
        eatInOrder.accept();
        EatInOrderStatus afterStatus = eatInOrder.getStatus();

        assertEquals(beforeStatus, EatInOrderStatus.WAITING);
        assertEquals(afterStatus, EatInOrderStatus.ACCEPTED);
    }

    @Test
    @DisplayName("대기중 상태가 아닌 주문은 수락할 수 없다.")
    void acceptNotWaiting() {
        // given
        UUID orderTableId = UUID.randomUUID();
        EatInOrder eatInOrder = EatIntOrderFixture.createEatInOrder(orderTableId);

        // when
        //then
        eatInOrder.accept();
        IllegalArgumentException aThrows = assertThrows(IllegalArgumentException.class, eatInOrder::accept);
        assertEquals("대기중 상태의 주문만 접수할 수 있습니다.", aThrows.getMessage());
    }
}
