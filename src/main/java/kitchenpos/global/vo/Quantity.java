package kitchenpos.global.vo;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
public class Quantity extends ValueObject {

    @Column(name = "quantity", nullable = false)
    @ColumnDefault("0")
    private long value;

    protected Quantity() {
    }

    public Quantity(long value) {
        validate(value);
        this.value = value;
    }

    private void validate(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
