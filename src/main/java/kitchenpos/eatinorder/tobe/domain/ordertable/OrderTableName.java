package kitchenpos.eatinorder.tobe.domain.ordertable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.exception.IllegalNameException;

import java.util.Objects;

@Embeddable
public class OrderTableName {
    @Column(name = "name", nullable = false)
    private String value;

    protected OrderTableName() {
    }

    public static OrderTableName of(String name) {
        validate(name);
        return new OrderTableName(name);
    }

    private OrderTableName(String name) {
        this.value = name;
    }

    private static void validate(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalNameException("이름은 빈값일 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableName that = (OrderTableName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
