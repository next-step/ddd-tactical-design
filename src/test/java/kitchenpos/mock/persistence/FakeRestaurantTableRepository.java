package kitchenpos.mock.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTable;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTableRepository;


public class FakeRestaurantTableRepository implements RestaurantTableRepository {

    private final Map<UUID, RestaurantTable> dataMap = new ConcurrentHashMap<>();

    @Override
    public RestaurantTable save(RestaurantTable menuGroup) {
        UUID id = menuGroup.getId();
        dataMap.put(id, menuGroup);
        return menuGroup;
    }

    @Override
    public Optional<RestaurantTable> findById(UUID id) {
        return dataMap.values()
            .stream()
            .filter(menu -> menu.getId().equals(id))
            .findFirst();
    }

    @Override
    public List<RestaurantTable> findAll() {
        return new ArrayList<>(dataMap.values());
    }
}
