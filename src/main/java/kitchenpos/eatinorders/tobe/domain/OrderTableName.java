package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderTableName {

    private String name;

    protected OrderTableName() {
    }

    public OrderTableName(final String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderTableName that = (OrderTableName) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
