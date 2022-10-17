package kitchenpos.eatinorders.domain.tobe;

import io.micrometer.core.instrument.util.StringUtils;
import java.util.Objects;

public class OrderTableName {
    private String value;

    public OrderTableName(final String value) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("orderTableName can not be empty");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderTableName that = (OrderTableName) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
