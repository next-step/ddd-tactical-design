package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;

import java.util.List;
import java.util.Optional;

public interface TobeOrderTableRepository {
    TobeOrderTable save(TobeOrderTable orderTable);

    Optional<TobeOrderTable> findById(OrderTableId id);

    List<TobeOrderTable> findAll();
}

