package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToBeOrderRepository {
    ToBeOrder save(ToBeOrder order);

    Optional<ToBeOrder> findById(UUID id);

    List<ToBeOrder> findAll();

    boolean existsByOrderTableAndStatusNot(ToBeOrderTable orderTable, ToBeOrderStatus status);
}

