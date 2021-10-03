package kitchenpos.eatinorders.tobe.domain.model;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class OrderLineItemQuantity {

    private BigDecimal value;

    public OrderLineItemQuantity(BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("올바르지 않은 수량으로 등록할 수 없습니다.");
        }
        this.value = value;
    }

    protected OrderLineItemQuantity() {

    }

    public BigDecimal getQuantity() {
        return this.value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLineItemQuantity)) return false;
        OrderLineItemQuantity that = (OrderLineItemQuantity) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
