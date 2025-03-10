package kitchenpos.eatinorders.tobe.infra.persistence.restaurant;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTable;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TobeJpaRestaurantTableRepository extends RestaurantTableRepository,
    JpaRepository<RestaurantTable, UUID> {

}
