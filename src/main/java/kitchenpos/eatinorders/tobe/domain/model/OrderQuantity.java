package kitchenpos.eatinorders.tobe.domain.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TODO 매장 주문은 주문 항목의 수량이 0 미만일 수 있다.
 */
@Embeddable
public class OrderQuantity {

    @Column(name = "quantity", nullable = false)
    private long value;

    protected OrderQuantity() {
    }

    public OrderQuantity(final long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderQuantity quantity = (OrderQuantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
