package kitchenpos.eatinorders.tobe.domain.ordertable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderTableName {
    @Column(name = "name", nullable = false)
    private String name;

    protected OrderTableName() {
    }

    public OrderTableName(String name) {
        validateOrderTableName(name);
        this.name = name;
    }

    private void validateOrderTableName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableName that = (OrderTableName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
