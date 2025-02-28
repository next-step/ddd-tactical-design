package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

public interface EatInOrderTable {
    UUID id();

    boolean isOccupiedValue();
}
