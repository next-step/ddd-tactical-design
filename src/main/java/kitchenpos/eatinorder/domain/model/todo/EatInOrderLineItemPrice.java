package kitchenpos.eatinorder.domain.model.todo;

import java.util.Objects;

public class EatInOrderLineItemPrice {
    private final long price;

    private EatInOrderLineItemPrice(long price) {
        this.price = price;
    }

    public static EatInOrderLineItemPrice of(long price) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이어야 합니다.");
        }
        return new EatInOrderLineItemPrice(price);
    }

    public long value() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItemPrice that = (EatInOrderLineItemPrice) o;
        return price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }
}
