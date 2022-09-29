package kitchenpos.global.exception;

public class NullPriceException extends IllegalArgumentException {

    public NullPriceException() {
        super("등록할 가격이 없다.");
    }
}
