package kitchenpos.eatinorders.tobe.domain.order.vo;

import java.util.UUID;

public class EatInOrderId {
    private final UUID value;

    public EatInOrderId() {
        this(UUID.randomUUID());
    }

    public EatInOrderId(final UUID value) {
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }
}
