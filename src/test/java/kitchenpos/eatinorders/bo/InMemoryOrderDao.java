package kitchenpos.eatinorders.bo;

import kitchenpos.eatinorders.dao.OrderDao;
import kitchenpos.eatinorders.model.Order;

import java.util.*;

public class InMemoryOrderDao implements OrderDao {
    private final Map<Long, Order> entities = new HashMap<>();

    @Override
    public Order save(final Order entity) {
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Order> findById(final Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public boolean existsByOrderTableIdAndOrderStatusIn(final Long orderTableId, final List<String> orderStatuses) {
        return entities.values()
                .stream()
                .filter(entity -> Objects.equals(entity.getOrderTableId(), orderTableId))
                .anyMatch(entity -> orderStatuses.contains(entity.getOrderStatus()))
                ;
    }

    @Override
    public boolean existsByOrderTableIdInAndOrderStatusIn(final List<Long> orderTableIds, final List<String> orderStatuses) {
        return entities.values()
                .stream()
                .filter(entity -> orderTableIds.contains(entity.getOrderTableId()))
                .anyMatch(entity -> orderStatuses.contains(entity.getOrderStatus()))
                ;
    }
}
