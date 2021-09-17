package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinordertables.domain.OrderTable;

import java.util.UUID;

public interface OrderTableTranslator {
    OrderTable getOrderTable(final UUID orderTableId);

    void clearOrderTable(final UUID orderTableId);
}
