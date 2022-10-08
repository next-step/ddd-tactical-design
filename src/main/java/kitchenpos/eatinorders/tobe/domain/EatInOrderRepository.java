package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.Optional;

public interface EatInOrderRepository {
    EatInOrder save(EatInOrder eatInOrder);

    Optional<EatInOrder> findById(Long id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableIdAndStatusNot(Long orderTableId, OrderStatus orderStatus);
}
