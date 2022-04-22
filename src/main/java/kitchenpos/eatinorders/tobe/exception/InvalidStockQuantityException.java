package kitchenpos.eatinorders.tobe.exception;

public class InvalidStockQuantityException extends RuntimeException {

    private static final long serialVersionUID = -6993478429826739820L;

    public InvalidStockQuantityException() {
        super("재고는 1 이상의 정수만 입력해주세요.");
    }
}
