package kitchenpos.eatinorder.domain.model.todo;

import java.util.Objects;

public class EatInOrderLineItemQuantity {
    private final long quantity;

    private EatInOrderLineItemQuantity(long quantity) {
        this.quantity = quantity;
    }

    public static EatInOrderLineItemQuantity of(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 0개 이상이어야 합니다.");
        }
        return new EatInOrderLineItemQuantity(quantity);
    }

    public long value() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItemQuantity that = (EatInOrderLineItemQuantity) o;
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(quantity);
    }
}
