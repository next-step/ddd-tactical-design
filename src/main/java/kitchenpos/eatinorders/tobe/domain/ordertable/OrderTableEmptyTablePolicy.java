package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.eatinorders.tobe.domain.order.EmptyTablePolicy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderTableEmptyTablePolicy implements EmptyTablePolicy {

    private final OrderTableRepository orderTableRepository;

    public OrderTableEmptyTablePolicy(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public boolean isEmptyTable(UUID orderTableId) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId).orElseThrow(IllegalArgumentException::new);
        return !orderTable.isOccupied();
    }
}
