package kitchenpos.deliveryorders.tobe.infra;

import kitchenpos.deliveryorders.tobe.domain.DeliveryOrder;
import kitchenpos.deliveryorders.tobe.domain.DeliveryOrderRepository;

import java.util.*;

public class InMemoryDeliveryOrderRepository implements DeliveryOrderRepository {

    private final Map<UUID, DeliveryOrder> orders = new HashMap<>();

    @Override
    public DeliveryOrder save(final DeliveryOrder order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<DeliveryOrder> findById(final UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<DeliveryOrder> findAll() {
        return new ArrayList<>(orders.values());
    }
}
