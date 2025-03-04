package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderTableNameException;

import java.util.Objects;

@Embeddable
public class OrderTableName {

    private String name;

    protected OrderTableName() {
    }

    public OrderTableName(final String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new InvalidOrderTableNameException("주문 테이블은 반드시 입력되어야 합니다");
        }
        this.name = name;
    }

    public String getValue() {
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
        return Objects.hashCode(name);
    }
}
