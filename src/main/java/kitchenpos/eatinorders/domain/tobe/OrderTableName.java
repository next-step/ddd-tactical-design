package kitchenpos.eatinorders.domain.tobe;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderTableName {

    private String name;

    protected OrderTableName() {
    }

    public OrderTableName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }
}
