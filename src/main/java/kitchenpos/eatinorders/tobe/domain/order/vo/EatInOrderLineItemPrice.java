package kitchenpos.eatinorders.tobe.domain.order.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class EatInOrderLineItemPrice {

    @Column(name = "price", nullable = false)
    private int price;

    protected EatInOrderLineItemPrice() {
    }

    public EatInOrderLineItemPrice(final int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EatInOrderLineItemPrice that)) return false;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
