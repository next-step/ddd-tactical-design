package kitchenpos.eatinorders.tobe.domain.validator;

import kitchenpos.eatinorders.tobe.domain.model.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;

import java.util.UUID;

public class OrderTableValidator {

    private final OrderRepository orderRepository;
    private final OrderTableRepository orderTableRepository;

    public OrderTableValidator(OrderRepository orderRepository, OrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    public void makeEmptyTable(UUID orderTableId) {
        if (isAllOrderCompleted(orderTableId)) {
            OrderTable orderTable = orderTableRepository.findById(orderTableId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 테이블입니다."));

            orderTable.setNumberOfGuests(new NumberOfGuests(0L));
            orderTable.clearTable();
        }
    }

    private boolean isAllOrderCompleted(UUID orderTableId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderTableId().equals(orderTableId))
                .allMatch(Order::isCompleted);
    }

}
