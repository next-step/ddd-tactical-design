package kitchenpos.eatinorders.domain;

import java.util.UUID;

public interface EatInOrderTableChecker {
    UUID validate(UUID orderTableId);
}
