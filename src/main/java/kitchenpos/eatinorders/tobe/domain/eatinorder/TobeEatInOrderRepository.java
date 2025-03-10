package kitchenpos.eatinorders.tobe.domain.eatinorder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeEatInOrderRepository {

    EatInOrder save(EatInOrder eatInOrder);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAll();

    boolean existsByRestaurantTableIdAndStatusNot(UUID restaurantTableId, EatInOrderStatus status);

}

