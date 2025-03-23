package kitchenpos.eatinorders.tobe.domain.orderTable;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderTableRepository {
    EatInOrderTable save(final EatInOrderTable orderTable);

    Optional<EatInOrderTable> findById(final UUID id);

    List<EatInOrderTable> findAll();
}
