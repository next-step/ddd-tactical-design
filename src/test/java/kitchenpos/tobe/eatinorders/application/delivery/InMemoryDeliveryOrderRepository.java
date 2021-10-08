package kitchenpos.tobe.eatinorders.application.delivery;

import static kitchenpos.tobe.eatinorders.application.Fixtures.INVALID_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.DeliveryOrder;
import kitchenpos.eatinorders.tobe.domain.repository.DeliveryOrderRepository;

public class InMemoryDeliveryOrderRepository implements DeliveryOrderRepository {

    private final Map<UUID, DeliveryOrder> deliveryOrders = new HashMap<>();

    @Override
    public DeliveryOrder save(DeliveryOrder deliveryOrder) {
        deliveryOrders.put(deliveryOrder.getId(), deliveryOrder);
        return deliveryOrder;
    }

    @Override
    public Optional<DeliveryOrder> findById(UUID id) {
        return Optional.ofNullable(deliveryOrders.get(id));
    }

    @Override
    public List<DeliveryOrder> findAll() {
        return new ArrayList<>(deliveryOrders.values());
    }
}
