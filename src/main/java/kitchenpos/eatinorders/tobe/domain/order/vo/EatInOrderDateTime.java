package kitchenpos.eatinorders.tobe.domain.order.vo;

import java.time.LocalDateTime;
import java.util.Objects;

public class EatInOrderDateTime {
    private final LocalDateTime value;

    public EatInOrderDateTime() {
        this(LocalDateTime.now());
    }

    public EatInOrderDateTime(final LocalDateTime value) {
        if(Objects.isNull(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EatInOrderDateTime that = (EatInOrderDateTime) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
