package kitchenpos.eatinorders.tobe.service;

import kitchenpos.eatinorders.tobe.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.entity.OrderTable;
import kitchenpos.eatinorders.tobe.repository.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.repository.OrderTableRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

public class EatInOrderDomainService {
    private final EatInOrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderDomainService(EatInOrderRepository orderRepository, OrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    public boolean isNotOccupiedOrderTable(EatInOrder order) {
        OrderTable orderTable = findOrderTableBy(order.getOrderTableId());
        return orderTable.isNotOccupied();
    }

    public void clearOrderTable(EatInOrder order) {
        OrderTable orderTable = findOrderTableBy(order.getOrderTableId());
        if (orderRepository.isAllCompleteByOrderTable(orderTable)) {
            orderTable.clear();
        }
    }

    private OrderTable findOrderTableBy(UUID id) {
        return orderTableRepository.findBy(id)
                .orElseThrow(() -> new NoSuchElementException());
    }
}
