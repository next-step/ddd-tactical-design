package kitchenpos.eatinorder.tobe.domain;

import kitchenpos.annotation.DomainService;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTableRepository;

import java.util.UUID;

@DomainService
public class LastEatInrderClearedTable implements ClearedTable {
    OrderRepository orderRepository;
    OrderTableRepository orderTableRepository;

    public LastEatInrderClearedTable(OrderRepository orderRepository, OrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public boolean isLastOrder(UUID tableId) {
        return !orderRepository.existsByOrderTableAndStatusNot(tableId, EatInOrderStatus.COMPLETED);
    }

}
