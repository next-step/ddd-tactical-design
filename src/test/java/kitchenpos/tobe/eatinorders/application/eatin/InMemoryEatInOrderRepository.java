package kitchenpos.tobe.eatinorders.application.eatin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.DeliveryOrder;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {

    private final Map<UUID, EatInOrder> eatInOrders = new HashMap<>();

    @Override
    public EatInOrder save(EatInOrder eatInOrder) {
        eatInOrders.put(eatInOrder.getId(), eatInOrder);
        return eatInOrder;
    }

    @Override
    public Optional<EatInOrder> findById(UUID id) {
        return Optional.ofNullable(eatInOrders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(eatInOrders.values());
    }

    @Override
    public boolean existsByOrderTableIdAndStatusNot(UUID orderTableId, EatInOrderStatus status) {
        return eatInOrders.values().stream().anyMatch(eatInOrder ->
                eatInOrder.getOrderTableId().equals(orderTableId) && eatInOrder.getStatus().equals(status)
        );
    }
}
