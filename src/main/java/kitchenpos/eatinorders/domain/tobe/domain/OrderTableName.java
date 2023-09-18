package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTableName {
    @Column(name = "name", nullable = false)
    private String name;

    protected OrderTableName() {
    }

    private OrderTableName(String name) {
        nullValidation(name);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderTableName that = (OrderTableName)o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static OrderTableName of(String name) {
        return new OrderTableName(name);
    }

    private void nullValidation(String name) {
        if (name == "" || Objects.isNull(name)) {
            throw new IllegalArgumentException("이름은 필수로 입력되야 합니다.");
        }
    }
}
