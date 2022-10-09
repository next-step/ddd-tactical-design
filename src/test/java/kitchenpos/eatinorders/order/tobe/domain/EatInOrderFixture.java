package kitchenpos.eatinorders.order.tobe.domain;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

public class EatInOrderFixture {

    public static EatInOrder create(final UUID orderId, final UUID orderTableId, final EatInOrderStatus status) {
        final EatInOrder eatInOrder = new EatInOrder();
        ReflectionTestUtils.setField(eatInOrder, "id", orderId);
        ReflectionTestUtils.setField(eatInOrder, "orderTableId", orderTableId);
        ReflectionTestUtils.setField(eatInOrder, "status", status);
        return eatInOrder;
    }
}
