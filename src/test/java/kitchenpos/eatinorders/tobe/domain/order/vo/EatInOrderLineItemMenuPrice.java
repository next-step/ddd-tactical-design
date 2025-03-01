package kitchenpos.eatinorders.tobe.domain.order.vo;

public class EatInOrderLineItemMenuPrice {
    private final int value;

    public EatInOrderLineItemMenuPrice(final int value) {
        this.value = value;
    }

    public boolean isSamePrice(final int value) {
        return this.value == value;
    }

    public int value() {
        return value;
    }
}
