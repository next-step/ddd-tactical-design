package kitchenpos.ordertables.application.service;

import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.eatinorders.domain.OrderTableId;
import kitchenpos.ordertables.application.EatInOrderStatusLoader;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultEatInOrderStatusLoader implements EatInOrderStatusLoader {

    private final EatInOrderRepository repository;

    public DefaultEatInOrderStatusLoader(EatInOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean areAllOrdersComplete(UUID orderTableId) {
        OrderTableId targetId = new OrderTableId(orderTableId);
        return repository.existsByOrderTableAndStatusNot(targetId, EatInOrderStatus.COMPLETED);

    }
}
