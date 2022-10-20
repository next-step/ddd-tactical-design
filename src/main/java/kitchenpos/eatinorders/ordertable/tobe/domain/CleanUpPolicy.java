package kitchenpos.eatinorders.ordertable.tobe.domain;

import java.util.UUID;

@FunctionalInterface
public interface CleanUpPolicy {

    boolean isCleanUpCondition(final UUID orderTableId);
}
