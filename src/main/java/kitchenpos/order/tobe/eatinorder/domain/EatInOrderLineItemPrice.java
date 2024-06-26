package kitchenpos.order.tobe.eatinorder.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class EatInOrderLineItemPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected EatInOrderLineItemPrice() {
    }

    public EatInOrderLineItemPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItemPrice that = (EatInOrderLineItemPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
