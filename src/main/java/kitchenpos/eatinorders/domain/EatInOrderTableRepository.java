package kitchenpos.eatinorders.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderTableRepository {
  EatInOrderTable save(EatInOrderTable orderTable);

  Optional<EatInOrderTable> findById(UUID id);

  List<EatInOrderTable> findAll();
}
