package kitchenpos.order.eatinorder.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

import static kitchenpos.order.eatinorder.exception.EatInOrderExceptionMessage.ORDER_TABLE_NAME_CREATION_EXCEPTION;

@Embeddable
public class OrderTableName {

    @Column(name = "name", nullable = false)
    private final String value;

    protected OrderTableName() {
        this.value = null;
    }

    public OrderTableName(String value) {
        validateOrderTableName(value);
        this.value = value;
    }

    private void validateOrderTableName(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(ORDER_TABLE_NAME_CREATION_EXCEPTION.getMessage());
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableName that = (OrderTableName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
