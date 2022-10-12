package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

public class EatInOrderFactory {

    private final EmptyTablePolicy emptyTablePolicy;

    public EatInOrderFactory(EmptyTablePolicy emptyTablePolicy) {
        this.emptyTablePolicy = emptyTablePolicy;
    }

    public EatInOrder createEatInOrder(UUID orderTableId, OrderLineItems orderLineItems) {
        if (emptyTablePolicy.isEmptyTable(orderTableId)) {
            throw new IllegalStateException("사용중인 주문테이블만 매장 주문이 가능합니다.");
        }

        return new EatInOrder(orderLineItems, orderTableId);
    }
}
