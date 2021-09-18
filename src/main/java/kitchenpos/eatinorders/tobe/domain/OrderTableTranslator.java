package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.dto.OrderTableResponse;

import java.util.UUID;

public interface OrderTableTranslator {
    OrderTableResponse getOrderTable(final UUID orderTableId);

    void clearOrderTable(final UUID orderTableId);
}
