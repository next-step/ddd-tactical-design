package kitchenpos.eatinorder.application.port.out;

import kitchenpos.eatinorder.domain.model.todo.OrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadOrderTablePort {
    Optional<OrderTable> findById(UUID id);
    List<OrderTable> findAll();
}
