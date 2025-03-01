package kitchenpos.eatinorders.tobe.domain.order.vo;

import java.time.LocalDateTime;

public class EatInOrderDateTime {
    private final LocalDateTime value;

    public EatInOrderDateTime() {
        this(LocalDateTime.now());
    }

    public EatInOrderDateTime(final LocalDateTime value) {
        this.value = value;
    }
}
