package kitchenpos.eatinorder.tobe.domain.order;

import kitchenpos.annotation.DomainService;

import java.util.UUID;

@DomainService
public class LastEatInrderClearedTable implements ClearedTable {
    EatInOrderRepository orderRepository;

    public LastEatInrderClearedTable(EatInOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean isLastOrder(UUID tableId) {
        return !orderRepository.existsByOrderTableAndStatusNot(tableId, EatInOrderStatus.COMPLETED);
    }

}
