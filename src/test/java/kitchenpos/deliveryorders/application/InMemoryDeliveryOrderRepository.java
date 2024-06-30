package kitchenpos.deliveryorders.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderRepository;

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
