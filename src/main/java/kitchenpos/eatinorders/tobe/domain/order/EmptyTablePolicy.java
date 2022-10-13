package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

@FunctionalInterface
public interface EmptyTablePolicy {
    boolean isEmptyTable(UUID orderTableId);
}
