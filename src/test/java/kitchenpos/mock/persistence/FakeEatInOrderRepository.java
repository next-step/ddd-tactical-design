package kitchenpos.mock.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.eatinorder.TobeEatInOrderRepository;


public class FakeEatInOrderRepository implements TobeEatInOrderRepository {

    private final Map<UUID, EatInOrder> dataMap = new ConcurrentHashMap<>();

    @Override
    public EatInOrder save(EatInOrder menuGroup) {
        UUID id = menuGroup.getId();
        dataMap.put(id, menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<EatInOrder> findById(UUID id) {
        return dataMap.values()
            .stream()
            .filter(menu -> menu.getId().equals(id))
            .findFirst();
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(dataMap.values());
    }

    @Override
    public boolean existsByRestaurantTableIdAndStatusNot(UUID restaurantTableId, EatInOrderStatus status) {
        return dataMap.values()
            .stream()
            .anyMatch(order -> order.getRestaurantTableId().equals(restaurantTableId) 
                && !order.getStatus().equals(status));
    }
}
