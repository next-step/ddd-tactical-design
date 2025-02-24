package kitchenpos.eatinorder.domain.model.todo;

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

    public long getQuantity() {
        return quantity;
    }
}
