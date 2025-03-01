package kitchenpos.eatinorder.application.port.in;

import kitchenpos.eatinorder.domain.model.todo.OrderTable;

import java.util.UUID;

public interface ClearOrderTableUseCase {
    OrderTable clear(final UUID orderTableId);
}
