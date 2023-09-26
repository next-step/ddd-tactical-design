package kitchenpos.eatinorders.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderTableName {
    @Column(name = "name", nullable = false)
    private String name;

    protected OrderTableName() {
    }

    public OrderTableName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getValue() {
        return name;
    }
}
