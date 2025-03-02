package kitchenpos.eatinorder.application.port.out;

import kitchenpos.eatinorder.domain.model.todo.OrderTable;

public interface SaveOrderTablePort {
    OrderTable save(OrderTable orderTable);
}
