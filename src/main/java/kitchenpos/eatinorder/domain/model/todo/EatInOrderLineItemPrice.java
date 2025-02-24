package kitchenpos.eatinorder.domain.model.todo;

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

    public long getPrice() {
        return price;
    }
}
