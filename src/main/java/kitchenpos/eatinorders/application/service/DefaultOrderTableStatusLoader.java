package kitchenpos.eatinorders.application.service;

import kitchenpos.eatinorders.application.OrderTableStatusLoader;
import kitchenpos.ordertables.application.OrderTableService;
import kitchenpos.ordertables.domain.OrderTableId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultOrderTableStatusLoader implements OrderTableStatusLoader {
    private OrderTableService orderTableService;

    public DefaultOrderTableStatusLoader(OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @Override
    public boolean isUnOccupied(UUID orderTableId) {
        return ! orderTableService.findById(new OrderTableId(orderTableId))
                .isOccupied();
    }
}
