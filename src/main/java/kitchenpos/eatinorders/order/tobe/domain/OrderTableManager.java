package kitchenpos.eatinorders.order.tobe.domain;

import java.util.UUID;

@FunctionalInterface
public interface OrderTableManager {

    boolean isEmptyTable(final UUID orderTableId);
}
