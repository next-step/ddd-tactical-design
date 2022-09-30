package kitchenpos.eatinorders.tobe.domain.vo;

import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import kitchenpos.global.vo.Quantity;
import kitchenpos.global.vo.ValueObject;

@Embeddable
public class NumberOfGuests implements ValueObject {

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "quantity", nullable = false)
    )
    private Quantity value;

    protected NumberOfGuests() {
    }

    public NumberOfGuests(Quantity value) {
        this.value = value;
    }

    public static NumberOfGuests zero() {
        return new NumberOfGuests(new Quantity(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NumberOfGuests that = (NumberOfGuests) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
