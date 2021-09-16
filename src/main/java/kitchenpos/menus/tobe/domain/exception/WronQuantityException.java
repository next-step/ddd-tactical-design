package kitchenpos.menus.tobe.domain.exception;

public class WronQuantityException extends RuntimeException {
    public static final String QUANTITY_SHOULD_BE_MORE_ONE = "수량은 1개 이상이어야 합니다.";

    public WronQuantityException() {
        super(QUANTITY_SHOULD_BE_MORE_ONE);
    }

    public WronQuantityException(final String message) {
        super(message);
    }
}
