package kitchenpos.eatinorders.tobe.domain.restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantTableRepository {

    RestaurantTable save(RestaurantTable orderTable);

    Optional<RestaurantTable> findById(UUID id);

    List<RestaurantTable> findAll();
}

