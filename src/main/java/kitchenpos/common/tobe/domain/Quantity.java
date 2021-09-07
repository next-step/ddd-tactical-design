package kitchenpos.common.tobe.domain;

public class Quantity {

    private final long quantity;

    public Quantity(final long quantity) {
        validate(quantity);
        this.quantity = quantity;
    }

    private void validate(final long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량 값이 필수고, 수량은 0 미만의 수량 값을 가질 수 없습니다");
        }
    }

    public long value() {
        return quantity;
    }
}
