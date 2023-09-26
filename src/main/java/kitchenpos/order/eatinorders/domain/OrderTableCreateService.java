package kitchenpos.order.eatinorders.domain;

import kitchenpos.order.domain.OrderTable;
import org.springframework.stereotype.Component;

@Component
public class OrderTableCreateService {

    private final OrderTableRepository orderTableRepository;

    public OrderTableCreateService(final OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    public OrderTable create(final OrderTable orderTable) {
        return orderTableRepository.save(new OrderTable(orderTable));
    }
}
