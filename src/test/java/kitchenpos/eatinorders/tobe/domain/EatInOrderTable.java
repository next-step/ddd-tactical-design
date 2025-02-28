package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public interface EatInOrderTable {
    UUID id();

    boolean isOccupiedValue();
}
