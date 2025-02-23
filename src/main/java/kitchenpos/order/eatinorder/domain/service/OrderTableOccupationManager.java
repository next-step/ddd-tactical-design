package kitchenpos.order.eatinorder.domain.service;

import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderTableOccupationManager {
    private final EatInOrderRepository eatInOrderRepository;

    public OrderTableOccupationManager(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    public void release(OrderTable orderTable) {
        if (!eatInOrderRepository.existsByOrderTableAndEatInOrderFlowNot(orderTable, EatInOrderFlow.COMPLETED)) {
            orderTable.releaseTable();
        }
    }
}
