package kitchenpos.eatinorders.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {
  EatInOrder save(EatInOrder order);

  Optional<EatInOrder> findById(UUID id);

  List<EatInOrder> findAll();

  boolean existsByOrderTableAndStatusNot(EatInOrderTable orderTable, EatInOrderStatus status);
}
