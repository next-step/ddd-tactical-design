package kitchenpos.common.domain.model;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Quantity {
    private BigDecimal value;

    public Quantity(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("올바르지 않은 수량으로 등록할 수 없습니다.");
        }
        this.value = value;
    }

    protected Quantity() {

    }

    public BigDecimal getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantity)) return false;
        Quantity quantity = (Quantity) o;
        return value.equals(quantity.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
