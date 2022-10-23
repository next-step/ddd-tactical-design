package kitchenpos.eatinorders.ordertable.tobe.domain;

import java.util.UUID;

@FunctionalInterface
public interface CleanUpPolicy {

    boolean isMatchCondition(final UUID orderTableId);
}
