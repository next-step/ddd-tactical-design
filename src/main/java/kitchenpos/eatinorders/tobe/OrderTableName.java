package kitchenpos.eatinorders.tobe;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTableName {

    private final String name;

    public OrderTableName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
