package kitchenpos.order.eatin.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.order.eatin.domain.exception.OrderTableNameException;

@Embeddable
public record OrderTableName(String name) {

    public static OrderTableName of(String name) {
        if (name == null || name.isBlank()) {
            throw new OrderTableNameException();
        }
        return new OrderTableName(name);
    }

    public String get() {
        return name;
    }
}
