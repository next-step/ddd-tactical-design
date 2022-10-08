package kitchenpos.eatinorders.ordertable.tobe.domain.exception;

public class NoUsedOrderTableException extends IllegalStateException {
    private static final String MESSAGE = "사용중인 주문 테이블이 아닙니다.";

    public NoUsedOrderTableException() {
        super(MESSAGE);
    }
}
