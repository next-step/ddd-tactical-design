package kitchenpos.order.eatinorders.domain.vo;

import kitchenpos.order.eatinorders.domain.exception.InvalidOrderTableNameException;
import kitchenpos.support.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderTableName extends ValueObject {

    private String name;

    public OrderTableName() {
    }

    public OrderTableName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new InvalidOrderTableNameException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
