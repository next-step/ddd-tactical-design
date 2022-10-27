package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

import static kitchenpos.menus.util.NameValidator.validateNameEmpty;

@Embeddable
public class OrderTableName {
    private String name;

    protected OrderTableName() {
    }

    public OrderTableName(String name) {
        validateNameEmpty(name);
        this.name = name;
    }

    public String name() {
        return name;
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
}
