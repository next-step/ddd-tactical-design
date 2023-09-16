package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToBeOrderTableRepository {
    ToBeOrderTable save(ToBeOrderTable orderTable);

    Optional<ToBeOrderTable> findById(UUID id);

    List<ToBeOrderTable> findAll();
}
