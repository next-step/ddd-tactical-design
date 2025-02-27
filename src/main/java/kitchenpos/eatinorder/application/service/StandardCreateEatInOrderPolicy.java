package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.domain.policy.CreateEatInOrderPolicy;
import kitchenpos.eatinorder.domain.model.todo.OrderTable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StandardCreateEatInOrderPolicy implements CreateEatInOrderPolicy {
    private final OrderTableService orderTableService;

    public StandardCreateEatInOrderPolicy(final OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @Override
    public void validateOrderTableAvailability(UUID orderTableId) {
        OrderTable orderTable = orderTableService.findById(orderTableId);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException("사용중인 주문 테이블 입니다. 주문 테이블은 비어있어야 합니다. orderTableId: " + orderTableId);
        }
    }
}
