package kitchenpos.order.eatinorder.domain.service;

import static kitchenpos.order.eatinorder.exception.EatInOrderExceptionMessage.RELEASE_ORDER_TABLE_EXCEPTION;

import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.model.ReleaseOrderTableEvent;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
public class OrderTableOccupationManager {
    private final EatInOrderRepository eatInOrderRepository;

    public OrderTableOccupationManager(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    @TransactionalEventListener
    public void release(ReleaseOrderTableEvent event) {
        OrderTable orderTable = event.orderTable();
        if (eatInOrderRepository.existsByOrderTableAndEatInOrderFlowNot(orderTable, EatInOrderFlow.COMPLETED)) {
            throw new IllegalStateException(RELEASE_ORDER_TABLE_EXCEPTION.getMessage());
        }
        orderTable.releaseTable();
    }
}
