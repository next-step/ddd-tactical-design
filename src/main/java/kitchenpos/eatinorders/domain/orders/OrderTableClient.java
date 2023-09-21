package kitchenpos.eatinorders.domain.orders;

import java.util.UUID;

public interface OrderTableClient {

    void validOrderTableIdForOrder(UUID orderTableId);

    void clearOrderTable(UUID orderTableId);
}
