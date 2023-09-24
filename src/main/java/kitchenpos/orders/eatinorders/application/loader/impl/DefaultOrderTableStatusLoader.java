package kitchenpos.orders.eatinorders.application.loader.impl;

import kitchenpos.orders.eatinorders.application.loader.OrderTableStatusLoader;
import kitchenpos.orders.ordertables.domain.OrderTable;
import kitchenpos.orders.ordertables.domain.OrderTableId;
import kitchenpos.orders.ordertables.domain.OrderTableRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class DefaultOrderTableStatusLoader implements OrderTableStatusLoader {
    private final OrderTableRepository orderTableRepository;

    public DefaultOrderTableStatusLoader(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Override
    public boolean isUnOccupied(UUID orderTableId) {
        OrderTable orderTable = orderTableRepository.findById(new OrderTableId(orderTableId))
                .orElseThrow(NoSuchElementException::new);
        return !orderTable.isOccupied();
    }
}
