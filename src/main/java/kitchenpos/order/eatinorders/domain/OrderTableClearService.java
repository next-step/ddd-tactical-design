package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderTable;
import org.springframework.stereotype.Component;


@Component
public class OrderTableClearService {

    private final OrderTableRepository orderTableRepository;

    public OrderTableClearService(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    public OrderTable clear(final Order order) {
        OrderTable orderTable = order.getOrderTable();
        if (!order.isCompleted()) {
            throw new IllegalStateException();
        }
        return orderTableRepository.save(orderTable.clear());
    }
}
