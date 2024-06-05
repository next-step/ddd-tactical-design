package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class EatInOrderCompletePolicy {
    private final EatInOrderRepository orderRepository;
    private final OrderTableRepository tableRepository;

    public EatInOrderCompletePolicy(EatInOrderRepository orderRepository, OrderTableRepository tableRepository) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
    }

    public void complete(EatInOrder order) {
        order.complete();
        List<EatInOrder> orders = orderRepository.findAllByOrderTableId(order.getOrderTableId());
        boolean allOrderCompleted = orders.stream()
                .allMatch(thisOrder -> thisOrder.isComplete());

        if (allOrderCompleted) {
            OrderTable table = tableRepository.findBy(order.getOrderTableId())
                    .orElseThrow(() -> new NoSuchElementException());
            table.clear();
        }
    }
}
