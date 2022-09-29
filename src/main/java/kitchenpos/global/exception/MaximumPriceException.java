package kitchenpos.global.exception;

public class MaximumPriceException extends IllegalArgumentException {

    public MaximumPriceException() {
        super("등록할 수 있는 가격이 초과되었습니다.");
    }
}
