package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.application.TobeOrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeOrderTableRepository {
    TobeOrderTable save(TobeOrderTable orderTable);

    Optional<TobeOrderTable> findById(UUID id);

    List<TobeOrderTable> findAll();
}
