package kitchenpos.eatinorders.tobe.domain.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class EatInOrderLineItemPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal eatInOrderLineItemPrice;

    protected EatInOrderLineItemPrice() {
    }

    public EatInOrderLineItemPrice(BigDecimal eatInOrderLineItemPrice) {
        this.eatInOrderLineItemPrice = eatInOrderLineItemPrice;
    }

    public BigDecimal getEatInOrderLineItemPrice() {
        return eatInOrderLineItemPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItemPrice that = (EatInOrderLineItemPrice) o;
        return Objects.equals(eatInOrderLineItemPrice, that.eatInOrderLineItemPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eatInOrderLineItemPrice);
    }
}
