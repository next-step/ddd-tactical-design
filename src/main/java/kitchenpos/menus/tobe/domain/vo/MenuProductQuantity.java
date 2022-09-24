package kitchenpos.menus.tobe.domain.vo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class MenuProductQuantity implements Serializable {

    @Column(name = "quantity", nullable = false)
    @ColumnDefault("0")
    private long value;

    protected MenuProductQuantity() {
    }

    public MenuProductQuantity(long value) {
        validate(value);
        this.value = value;
    }

    private void validate(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuProductQuantity that = (MenuProductQuantity) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
