package kitchenpos.eatinorders.tobe.domain.model;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTableName {

    private String name;

    public OrderTableName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
