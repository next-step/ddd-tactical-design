package kitchenpos.eatinorders.tobe.domain.vo;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.global.vo.ValueObject;

@Embeddable
public class EatInOrderQuantity implements ValueObject {

    @Column(name = "quantity", nullable = false)
    private long value;

    protected EatInOrderQuantity() {
    }

    public EatInOrderQuantity(long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EatInOrderQuantity that = (EatInOrderQuantity) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
