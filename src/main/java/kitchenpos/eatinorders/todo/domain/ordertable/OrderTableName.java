package kitchenpos.eatinorders.todo.domain.ordertable;

import jakarta.persistence.Embeddable;
import kitchenpos.eatinorders.exception.KitchenPosIllegalArgumentException;

import java.util.Objects;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.INVALID_ORDER_TABLE_NAME;

@Embeddable
public class OrderTableName {
    private String name;

    protected OrderTableName() {
    }

    protected OrderTableName(String name) {
        this.name = name;
    }

    public static OrderTableName from(String name) {
        checkNullOrBlank(name);
        return new OrderTableName(name);
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

    public String nameValue() {
        return name;
    }

    private static void checkNullOrBlank(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new KitchenPosIllegalArgumentException(INVALID_ORDER_TABLE_NAME);
        }
    }
}
