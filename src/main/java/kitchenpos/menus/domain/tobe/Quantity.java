package kitchenpos.menus.domain.tobe;

public class Quantity {
    private final int value;

    public Quantity(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("메뉴에 속한 상품의 수량은 0 이상이어야 한다.");
        }
    }

    public int getValue() {
        return value;
    }
}
