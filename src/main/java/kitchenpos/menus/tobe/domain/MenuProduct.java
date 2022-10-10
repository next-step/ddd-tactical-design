package kitchenpos.menus.tobe.domain;

public class MenuProduct {
    private static final String LESS_THAN_ZERO_MESSAGE = "수량은 0보다 작을 수 없습니다.";

    private long quantity;

    public MenuProduct(final long quantity) {
        if (quantity < 0) {
            new IllegalArgumentException(LESS_THAN_ZERO_MESSAGE);
        }
        this.quantity = quantity;
    }
}
