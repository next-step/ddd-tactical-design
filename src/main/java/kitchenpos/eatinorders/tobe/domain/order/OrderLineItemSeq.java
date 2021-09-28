package kitchenpos.eatinorders.tobe.domain.order;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class OrderLineItemSeq implements Serializable {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long value;

    protected OrderLineItemSeq() {
    }

    public OrderLineItemSeq(final Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderLineItemSeq that = (OrderLineItemSeq) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
