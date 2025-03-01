package kitchenpos.eatinorders.tobe.domain.ordertable.vo;

import java.util.Objects;

public class OrderTableName {
    private final String value;

    public OrderTableName(final String value) {
        if(Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OrderTableName that = (OrderTableName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
