package kitchenpos.order.eatin.domain.model;

import jakarta.persistence.Embeddable;
import kitchenpos.menu.domain.exception.MenuNameException;

@Embeddable
public record OrderTableName(String name) {

    public static OrderTableName of(String name) {
        if (name == null || name.isBlank()) {
            throw new MenuNameException();
        }
        return new OrderTableName(name);
    }

    public String get() {
        return name;
    }
}
